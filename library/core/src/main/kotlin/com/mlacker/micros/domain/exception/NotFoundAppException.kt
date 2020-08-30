package com.mlacker.micros.domain.exception

/**
 * 没有找到异常
 */
class NotFoundAppException @JvmOverloads constructor(message: String = "未找到相应的数据", cause: Throwable? = null) : ApplicationException(message, cause)