package com.crawler.components;

import com.crawler.annotation.RequirePermissions;
import com.crawler.annotation.RequireToken;
import com.crawler.domain.TokenEntity;
import com.crawler.exception.SecurityException;
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
import java.util.Map;

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
    private void webLog() {
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
        Class<?> classTarget = proceedingJoinPoint.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterTypes();
        // 得到方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        Method objMethod = classTarget.getMethod(methodName, par);
        RequireToken requireToken = objMethod.getAnnotation(RequireToken.class);
        // 需要验证token
        if (null != requireToken) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            // 得到请求参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (ObjectUtils.isEmpty(parameterMap.get("uid"))
                    || ObjectUtils.isEmpty(parameterMap.get("timestamp"))
                    || ObjectUtils.isEmpty(parameterMap.get("token"))) {
                throw new SecurityException("insecurity access");
            }
            // 设置请求参数
            TokenEntity te = new TokenEntity();
            te.setUid(parameterMap.get("uid")[0]);
            te.setTimestamp(parameterMap.get("timestamp")[0]);
            te.setToken(parameterMap.get("token")[0]);
            checkToken.checkToken(te);
            uid = te.getUid();
        }
        RequirePermissions requirePermissions = objMethod.getAnnotation(RequirePermissions.class);
        if (null != requirePermissions) {
            // 得到权限字符串的值
            int[] values = requirePermissions.value();
            // 验证该用户是否有这个字符串所代表的权限
            checkPermissions.checkPermissions(uid, values);
        }
        long timeStart = SystemClock.now();
        Object obj = proceedingJoinPoint.proceed();
        LoggerUtils.printLogger(logger, "环绕通知的目标方法名：" + methodName);
        LoggerUtils.printLogger(logger, "方法执行时间为：" + String.valueOf(SystemClock.now() - timeStart) + "ms");
        return obj;
    }
}
