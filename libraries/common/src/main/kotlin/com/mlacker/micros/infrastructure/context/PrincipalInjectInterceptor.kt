package com.mlacker.micros.infrastructure.context

import com.mlacker.micros.infrastructure.context.PrincipalHolder.context
import com.mlacker.micros.infrastructure.context.PrincipalHolder.hasContext
import feign.RequestInterceptor
import feign.RequestTemplate
import java.net.URLEncoder

class PrincipalInjectInterceptor : RequestInterceptor {

    override fun apply(requestTemplate: RequestTemplate) {
        if (hasContext()) {
            val (id, name, applicationId) = context
            requestTemplate.header(PrincipalConstants.USER_ID, id.toString())
            requestTemplate.header(PrincipalConstants.USER_NAME, URLEncoder.encode(name, Charsets.UTF_8))
            requestTemplate.header(PrincipalConstants.APPLICATION_ID, applicationId.toString())
        }
    }
}