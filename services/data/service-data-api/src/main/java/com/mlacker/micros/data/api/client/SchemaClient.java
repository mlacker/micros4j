package com.mlacker.micros.data.api.client;

import com.mlacker.micros.data.api.model.schema.TableModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-data/schema")
public interface SchemaClient {

    @GetMapping("{id}")
    TableModel findSchema(@PathVariable Long id);
}
