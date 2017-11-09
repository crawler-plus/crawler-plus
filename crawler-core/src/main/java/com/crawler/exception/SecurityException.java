package com.crawler.exception;

/**
 * 权限Exception
 */
public class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }
}
