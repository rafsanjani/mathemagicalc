package com.foreverrafs.core.exceptions

/**
 * Thrown when an equation is improperly formatted
 */
class InvalidEquationException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}