package com.foreverrafs.numericals.core;

/**
 * Thrown when an equation is improperly formatted
 */
public class InvalidEquationException extends RuntimeException {
    public InvalidEquationException(String message) {
        super(message);
    }

    public InvalidEquationException(String message, Throwable cause) {
        super(message, cause);
    }
}
