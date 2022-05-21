package com.mlacker.micros.domain.exception

/**
 * 应用异常
 */
open class ApplicationException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)