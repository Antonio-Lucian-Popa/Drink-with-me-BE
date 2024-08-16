package com.asusoftware.Drink_with_me.exception;

public class UnauthorizedUserException extends RuntimeException {

    public UnauthorizedUserException() {
        super();
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }

    public UnauthorizedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedUserException(Throwable cause) {
        super(cause);
    }

    // If you're using Java 7 or later, you can add these constructors for completeness
    protected UnauthorizedUserException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
