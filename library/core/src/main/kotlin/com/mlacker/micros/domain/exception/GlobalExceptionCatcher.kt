package com.mlacker.micros.domain.exception

import com.mlacker.micros.model.ErrorModel
import com.netflix.hystrix.exception.HystrixRuntimeException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionCatcher {

    private val logger = LoggerFactory.getLogger(GlobalExceptionCatcher::class.java)

    /**
     * 应用异常拦截
     *
     * @param exception 应用异常
     * @return 400 Bad Request
     */
    @ExceptionHandler(ApplicationException::class)
    fun applicationException(exception: ApplicationException) =
            ResponseEntity.badRequest().body(ErrorModel(exception.message))

    /**
     * 实体不存在异常拦截
     *
     * @param exception 实体不存在异常
     * @return 404 Not Fount
     */
    @ExceptionHandler(NotFoundAppException::class)
    fun notFoundException(exception: NotFoundAppException) =
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorModel(exception.message))

    /**
     * 远程调用异常拦截
     *
     * @param exception Hystrix 异常
     * @return 返回原请求的响应状态码及消息
     */
    @ExceptionHandler(HystrixRuntimeException::class)
    fun hystrixException(exception: HystrixRuntimeException): ResponseEntity<String> {
        val cause = exception.cause
        return if (cause is ForwardHttpException) {
            ResponseEntity.status(cause.status).body(cause.message)
        } else {
            ResponseEntity.status(500).body(exception.message)
        }
    }

    @ExceptionHandler(Throwable::class)
    fun throwableException(throwable: Throwable): ResponseEntity<ErrorModel> {
        logger.error(throwable.message, throwable)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorModel(throwable.message, throwable))
    }
}