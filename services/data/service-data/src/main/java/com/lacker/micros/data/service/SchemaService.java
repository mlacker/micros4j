package com.lacker.micros.data.service;

import com.lacker.micros.data.api.model.SchemaModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemaService {

    public SchemaModel findSchema(List<String> tableIds) {
        return new SchemaModel();
    }
}
