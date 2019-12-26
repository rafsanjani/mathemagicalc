package com.foreverrafs.core.exceptions

class InvalidIntervalException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}