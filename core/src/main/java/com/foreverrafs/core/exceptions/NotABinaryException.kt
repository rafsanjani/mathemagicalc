package com.foreverrafs.core.exceptions

class NotABinaryException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}