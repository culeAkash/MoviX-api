package com.project.api.gateway.filter;

import com.project.api.gateway.exceptions.AuthenticationException;
import com.project.api.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {
    @Autowired
    private JwtUtil jwtUtil;

    private final List<String> PUBLIC_API_ENDPOINTS = List.of("/auth/login", "/auth/register", "/eureka");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        Predicate<ServerHttpRequest> isApiSecured = r -> PUBLIC_API_ENDPOINTS.stream()
                .noneMatch(uri->r.getURI().getPath().contains(uri));

        if(isApiSecured.test(request)){
            if(authMissing(request)) throw new AuthenticationException("Authentication header is missing");

            String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            if(token!=null && token.startsWith("Bearer "))token = token.substring(7);

            jwtUtil.validateToken(token);
        }

        return chain.filter(exchange);
    }


    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
