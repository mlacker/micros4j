package com.mlacker.micros.domain.exception

import com.mlacker.micros.model.ErrorModel
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
            .also { logger.warn(exception.message, exception) }

    /**
     * 远程调用异常拦截
     *
     * @param exception Hystrix 异常
     * @return 返回原请求的响应状态码及消息
     */
    @ExceptionHandler(ForwardHttpException::class)
    fun hystrixException(exception: ForwardHttpException) =
        ResponseEntity.status(exception.status).body(exception.message)

    @ExceptionHandler(Throwable::class)
    fun throwableException(throwable: Throwable) = ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorModel(throwable.message, throwable))
        .also { logger.error(throwable.message, throwable) }
}