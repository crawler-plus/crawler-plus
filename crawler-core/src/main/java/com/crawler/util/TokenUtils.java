package com.crawler.util;

import com.crawler.domain.TokenEntity;
import com.xiaoleilu.hutool.date.SystemClock;
import com.xiaoleilu.hutool.json.JSONUtil;

import java.util.Base64;

/**
 * Token工具类
 */
public class TokenUtils {

    /**
     * 根据用户id，密钥生成token
     * @param tokenKey
     * @return
     */
   public static TokenEntity createUserToken(String userId, long timestamp, String tokenKey) {
       TokenEntity te = new TokenEntity();
       te.setUid(userId);
       if(timestamp == 0L) {
           te.setTimestamp(String.valueOf(SystemClock.now()));
       }else {
           te.setTimestamp(String.valueOf(timestamp));
       }
       te.setTokenKey(tokenKey);
       // 生成签名
       String sign = Base64.getEncoder().encodeToString(JSONUtil.toJsonStr(te).getBytes());
       te.setToken(sign);
       return te;
   }
}
