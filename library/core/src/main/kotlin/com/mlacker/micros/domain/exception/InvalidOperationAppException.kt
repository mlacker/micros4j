package com.mlacker.micros.domain.exception

/**
 * 无效的操作异常
 */
class InvalidOperationAppException @JvmOverloads constructor(message: String = "无效的操作", cause: Throwable? = null) : ApplicationException(message, cause)