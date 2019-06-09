package com.lacker.micros.data.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("service-data/api/schema")
public interface SchemaClient {

    @GetMapping
    String findSchema();
}
