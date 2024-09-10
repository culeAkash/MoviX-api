package com.project.review.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "movie-service",path = "/api/v1/movies")
public interface MovieServiceClient {
}
