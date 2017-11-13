package com.crawler.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 */
public class MD5Utils {

    public static String toMD5String(String source, String salt) {
        String afterStr = source + salt;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BASE64Encoder base64en = new BASE64Encoder();
        // 加密后的字符串
        String newStr = null;
        try {
            if (md5 != null) {
                newStr = base64en.encode(md5.digest(afterStr.getBytes("utf-8")));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newStr;
    }
}
