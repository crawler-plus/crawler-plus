package com.crawler.components;

import com.crawler.domain.TokenEntity;
import com.crawler.service.RPCApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 验证token公共类
 */
@Component
public class CheckToken {

    @Autowired
    private RPCApi rpcApi;

    void checkToken(TokenEntity te) {
        // token不合法标识
        boolean tokenInvalidFlag = false;
        // token
        String token = te.getToken();
        // 用户id
        String uid = te.getUid();
        // 得到token的值
        String tokenVal = rpcApi.getUserToken("userToken:" + uid + ":" + token);
        // 如果token的值为空
        if(StringUtils.isEmpty(tokenVal)) {
            tokenInvalidFlag = true;
        }
        // 如果token不合法
        if(tokenInvalidFlag) {
            throw new SecurityException("insecurity access");
        }
    }
}
