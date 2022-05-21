package com.mlacker.micros.gateway.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.mlacker.micros.config.properties.TokenProperties
import com.mlacker.micros.infrastructure.context.PrincipalConstants
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.net.URLEncoder
import java.nio.charset.Charset

@Component
class TokenFilter protected constructor(properties: TokenProperties) : GlobalFilter, Ordered {

    companion object {
        private const val TOKEN_SCHEME = "Bearer "
    }

    private val pathMatcher: AntPathMatcher = AntPathMatcher()
    private val skipPaths: List<String> = listOf("/auth-api/auth/login", *properties.skipPaths.toTypedArray())
    private val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(properties.signingKey))
        .withIssuer(properties.issuer)
        .build()

    override fun getOrder(): Int = -100

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request

        if (skipFilter(request.path.value())) {
            return chain.filter(exchange)
        }

        val token = tokenExtract(request) ?: return Mono.error(IllegalArgumentException("Token is required"))

        return try {
            val decodedJWT = verifier.verify(token)
            val userId = decodedJWT.subject
            val userName = decodedJWT.getClaim("name").asString()
            val applicationId = decodedJWT.getClaim("app").asString()
            val mutableRequest = exchange.request.mutate()
                .header(PrincipalConstants.USER_ID, userId)
                .header(PrincipalConstants.USER_NAME, URLEncoder.encode(userName, Charset.defaultCharset()))
                .header(PrincipalConstants.APPLICATION_ID, applicationId)
                .build()
            chain.filter(exchange.mutate().request(mutableRequest).build())
        } catch (ex: JWTVerificationException) {
            sendUnauthorizedResponse(exchange.response)
        }
    }

    private fun skipFilter(path: String): Boolean {
        return skipPaths.stream().anyMatch { pathMatcher.match(it, path) }
    }

    private fun tokenExtract(request: ServerHttpRequest): String? {
        val authorization = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        return if (!StringUtils.hasText(authorization)) {
            null
        } else authorization!!.substring(TOKEN_SCHEME.length).trim { it <= ' ' }
    }

    private fun sendUnauthorizedResponse(response: ServerHttpResponse): Mono<Void> {
        response.statusCode = HttpStatus.UNAUTHORIZED
        return response.setComplete()
    }
}