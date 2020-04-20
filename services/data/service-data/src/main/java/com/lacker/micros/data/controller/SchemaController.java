package com.lacker.micros.data.controller;

import com.lacker.micros.data.api.client.SchemaClient;
import com.lacker.micros.data.api.model.schema.TableModel;
import com.lacker.micros.data.service.SchemaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schema")
public class SchemaController implements SchemaClient {

    private final SchemaService service;

    SchemaController(SchemaService service) {
        this.service = service;
    }

    @GetMapping
    public List<TableModel> findAll() {
        return service.findAll();
    }

    @Override
    public TableModel findSchema(Long id) {
        return service.findSchema(id);
    }
}
