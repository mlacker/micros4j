package com.mlacker.micros.model

class ErrorModel(val message: String?, private val cause: Throwable? = null) {

    val details: String? = cause?.cause?.message

    val stackTrace
        get() = cause?.stackTrace?.filter { it.className.startsWith("com.mlacker.micros") }?.map { it.toString() }
}