package com.lacker.micros.controller;

import com.lacker.micros.domain.exception.ApplicationException;
import com.lacker.micros.domain.exception.FeignException;
import com.lacker.micros.domain.exception.NotFoundAppException;
import com.lacker.micros.model.ErrorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ThrowableHandler {

    private static Logger logger = LoggerFactory.getLogger(ThrowableHandler.class);

    /**
     * 应用异常拦截
     *
     * @param exception 应用异常
     * @return 400 Bad Request
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorModel> applicationException(ApplicationException exception) {
        logger.warn(exception.getMessage());

        return ResponseEntity.badRequest().body(new ErrorModel(exception.getMessage()));
    }

    /**
     * 实体不存在异常拦截
     *
     * @param exception 实体不存在异常
     * @return 404 Not Fount
     */
    @ExceptionHandler(NotFoundAppException.class)
    public ResponseEntity<ErrorModel> notFoundException(NotFoundAppException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(exception.getMessage()));
    }

    /**
     * 远程调用异常拦截
     *
     * @param exception Feign 异常
     * @return 返回原请求的响应状态码及消息
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> feignException(FeignException exception) {
        return ResponseEntity.status(exception.status()).body(exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorModel> throwableException(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorModel(throwable.getMessage(), throwable));
    }
}
