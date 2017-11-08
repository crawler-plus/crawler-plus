package com.crawler.exception;

/**
 * 无操作权限Exception
 */
public class PermissionInvalidException extends RuntimeException {
    public PermissionInvalidException(String message) {
        super(message);
    }
}
