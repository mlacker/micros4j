package com.mlacker.micros.config

import com.mlacker.micros.domain.exception.ForwardHttpException
import feign.Response
import feign.Util
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException

@Configuration
class FeignConfig {

    @Bean
    fun errorDecoder() = ErrorDecoder { methodKey: String, response: Response ->
        var message: String? = ""
        try {
            message = Util.toString(response.body()?.asReader(Util.UTF_8))
        } catch (ignored: IOException) {
        }
        ForwardHttpException(response.status(), message)
    }
}