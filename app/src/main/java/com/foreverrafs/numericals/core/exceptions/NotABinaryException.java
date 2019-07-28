package com.foreverrafs.numericals.core.exceptions;

public class NotABinaryException extends RuntimeException {
    public NotABinaryException(String message) {
        super(message);
    }

    public NotABinaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
