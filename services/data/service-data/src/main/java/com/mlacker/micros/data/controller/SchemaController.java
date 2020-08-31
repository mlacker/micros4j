package com.mlacker.micros.data.controller;

import com.mlacker.micros.data.api.client.SchemaClient;
import com.mlacker.micros.data.api.model.schema.TableModel;
import com.mlacker.micros.data.service.SchemaService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
