package com.crawler.components;

import com.crawler.annotation.RequirePermissions;
import com.crawler.domain.TokenEntity;
import com.crawler.util.LoggerUtils;
import com.xiaoleilu.hutool.date.SystemClock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * AOP配置
 */
@Component
@Aspect
public class WebAopConfiguration {

    @Autowired
    private CheckToken checkToken;

    @Autowired
    private CheckPermissions checkPermissions;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.crawler.controller.*.*(..))")
    private void webLog(){
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LoggerUtils.printDebugLogger(logger, "URL : " + request.getRequestURL().toString());
        LoggerUtils.printDebugLogger(logger, "HTTP_METHOD : " + request.getMethod());
        LoggerUtils.printDebugLogger(logger, "IP : " + request.getRemoteAddr());
        LoggerUtils.printDebugLogger(logger, "CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LoggerUtils.printDebugLogger(logger, "ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 环绕通知
     */
    @Around("webLog()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 用户id
        String uid = "";
        // 获得方法的所有参数
        Object[] args = proceedingJoinPoint.getArgs();
        if(!ObjectUtils.isEmpty(args)) {
            for(Object o : args) {
                if(o instanceof TokenEntity) {
                    TokenEntity t = (TokenEntity)o;
                    uid = t.getUid();
                    // 验证token
                    checkToken.checkToken(t);
                }
            }
        }
        Class<?> classTarget = proceedingJoinPoint.getTarget().getClass();
        Class<?>[] par = ((MethodSignature)proceedingJoinPoint.getSignature()).getParameterTypes();
        // 得到方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        Method objMethod=classTarget.getMethod(methodName, par);
        RequirePermissions requirePermissions = objMethod.getAnnotation(RequirePermissions.class);
        if(null != requirePermissions) {
            // 得到权限字符串的值
            String[] values = requirePermissions.value();
            // 验证该用户是否有这个字符串所代表的权限
            checkPermissions.checkPermissions(uid, values);
        }
        long timeStart = SystemClock.now();
        Object obj = proceedingJoinPoint.proceed(args);
        LoggerUtils.printLogger(logger, "环绕通知的目标方法名：" + methodName);
        LoggerUtils.printLogger(logger, "方法执行时间为：" + String.valueOf(SystemClock.now() - timeStart) + "ms");
        return obj;
    }
}
