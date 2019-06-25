package com.lacker.micros.gateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lacker.micros.config.properties.TokenProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class TokenFilter implements GlobalFilter, Ordered {

    private final static String TOKEN_SCHEME = "Bearer ";

    private final AntPathMatcher pathMatcher;
    private final Collection<String> skipPaths;
    private final JWTVerifier verifier;

    protected TokenFilter(TokenProperties properties) {
        this.pathMatcher = new AntPathMatcher();
        this.skipPaths = new ArrayList<>();
        this.skipPaths.add("/auth-api/auth/login");
        this.skipPaths.addAll(properties.getSkipPaths());

        this.verifier = JWT.require(Algorithm.HMAC256(properties.getSigningKey()))
                .withIssuer(properties.getIssuer())
                .build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (skipFilter(request.getPath().value())) {
            return chain.filter(exchange);
        }

        String token = tokenExtract(request);

        if (token == null) {
            return Mono.error(new IllegalArgumentException("Token is required"));
        }

        try {
            DecodedJWT decodedJWT = verifier.verify(token);

            String userId = decodedJWT.getSubject();
            String applicationId = decodedJWT.getClaim("app").asString();

            ServerHttpRequest mutableRequest = exchange.getRequest().mutate()
                    .header("X-UserId", userId)
                    .header("X-ApplicationId", applicationId)
                    .build();
            return chain.filter(exchange.mutate().request(mutableRequest).build());
        } catch (JWTVerificationException ex) {
            return sendUnauthorizedResponse(exchange.getResponse());
        }
    }


    @Override
    public int getOrder() {
        return -100;
    }

    private boolean skipFilter(String path) {
        return skipPaths.stream().anyMatch(m -> pathMatcher.match(m, path));
    }

    private String tokenExtract(ServerHttpRequest request) {
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(authorization)) {
            return null;
        }

        return authorization.substring(TOKEN_SCHEME.length()).trim();
    }

    private Mono<Void> sendUnauthorizedResponse(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
