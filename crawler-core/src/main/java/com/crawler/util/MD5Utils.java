package com.crawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 */
public class MD5Utils {

    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    public static String toMD5String(String source, String salt) {
        String afterStr = source + salt;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LoggerUtils.printExceptionLogger(logger, e);
        }
        BASE64Encoder base64en = new BASE64Encoder();
        // 加密后的字符串
        String newStr = null;
        try {
            if (md5 != null) {
                newStr = base64en.encode(md5.digest(afterStr.getBytes("utf-8")));
            }
        } catch (UnsupportedEncodingException e) {
            LoggerUtils.printExceptionLogger(logger, e);
        }
        return newStr;
    }
}
