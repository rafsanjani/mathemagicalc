package com.foreverrafs.core.exceptions;

public class InvalidIntervalException extends RuntimeException {
    public InvalidIntervalException(String message) {
        super(message);
    }

    public InvalidIntervalException(String message, Throwable cause) {
        super(message, cause);
    }
}
