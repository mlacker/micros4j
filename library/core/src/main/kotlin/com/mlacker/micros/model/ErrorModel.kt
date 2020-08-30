package com.mlacker.micros.model

class ErrorModel(val message: String?, val cause: Throwable? = null) {

    val details: String? = cause?.message

    val stackTrace
        get() = cause?.stackTrace?.filter { it.className.startsWith("com.mlacker.micros") }?.map { it.toString() }
}