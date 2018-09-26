package com.foreverrafs.numericals.core;

class NotABinaryException extends RuntimeException {
    public NotABinaryException(String message) {
        super(message);
    }

    public NotABinaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
