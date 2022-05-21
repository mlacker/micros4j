package com.mlacker.micros.domain.exception

/**
 * 外套异常
 */
class WrapperAppException(throwable: Throwable) : ApplicationException(throwable.message, throwable) {
    var data: MutableMap<String, Any> = LinkedHashMap()

    override fun getStackTrace(): Array<StackTraceElement> = cause!!.stackTrace
}