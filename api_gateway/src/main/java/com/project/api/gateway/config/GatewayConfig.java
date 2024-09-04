package com.project.api.gateway.config;

import com.project.api.gateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("public-user-service", r -> r.path("/api/v1/users/public/**")
                        .uri("lb://user-service"))
                .route("private-user-service", r -> r.path("/api/v1/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route("auth-service",r->r.path("/api/v1/auth/**")
                        .uri("lb://auth-service"))
                .route("file-service",r->r.path("/api/v1/file-storage/**")
                        .uri("lb://file-service")
                )
                .build();
    }
}