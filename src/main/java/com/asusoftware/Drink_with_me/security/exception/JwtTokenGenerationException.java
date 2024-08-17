package com.asusoftware.Drink_with_me.security.exception;

public class JwtTokenGenerationException extends RuntimeException {
    public JwtTokenGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}

