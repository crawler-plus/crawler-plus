package com.crawler.exception;

/**
 * Token不存在Exception
 */
public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String message) {
        super(message);
    }
}
