package com.lacker.micros.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class TokenFilter implements GlobalFilter, Ordered {

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private Collection<String> skipPaths;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (skipFilter(request.getPath().value())) {
            return chain.filter(exchange);
        }

        String token = tokenExtract(request);

        if (token == null) {
            sendUnauthorizedResponse(exchange.getResponse());
        }

        ServerHttpRequest mutableRequest = exchange.getRequest().mutate()
                .header("X-UserId", "")
                .header("X-ApplicationId", "")
                .build();
        return chain.filter(exchange.mutate().request(mutableRequest).build());
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private boolean skipFilter(String path) {
        if (pathMatcher.match("/auth-api/auth/**", path)) {
            return true;
        }

        return skipPaths.stream().anyMatch(path::startsWith);
    }

    private String tokenExtract(ServerHttpRequest request) {
        String authorization = request.getHeaders().getFirst("Authorization");
        return null;
    }

    private void sendUnauthorizedResponse(ServerHttpResponse response) {
    }
}
