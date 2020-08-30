package com.mlacker.micros.domain.exception

/**
 * 无效的参数异常
 */
class InvalidParameterAppException @JvmOverloads constructor(message: String = "无效的参数", cause: Throwable? = null) : ApplicationException(message, cause)