package com.lacker.micros.data.controller;

import com.lacker.micros.data.api.client.SchemaClient;
import com.lacker.micros.data.service.SchemaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schema")
public class SchemaController implements SchemaClient {

    private final SchemaService service;

    SchemaController(SchemaService service) {

        this.service = service;
    }

    @Override
    public String findSchema() {
        return "test";
    }
}
