package com.lacker.micros.data.controller;

import com.lacker.micros.data.api.client.FormDataClient;
import com.lacker.micros.data.api.client.ReportDataClient;
import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.data.QueryModel;
import com.lacker.micros.data.api.model.schema.SchemaModel;
import com.lacker.micros.data.service.DataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataController implements FormDataClient, ReportDataClient {

    private final DataService service;

    public DataController(DataService service) {
        this.service = service;
    }

    @Override
    public List<DataModel> load(String id, SchemaModel model) {
        return service.load(id, model);
    }

    @Override
    public void save(List<DataModel> models) {
        service.save(models);
    }

    @Override
    public void delete(String id, String tableId) {
        service.delete(id, tableId);
    }

    @Override
    public List<Map<String, Object>> query(QueryModel model) {
        return service.query(model);
    }

    @Override
    public Long queryCount(QueryModel model) {
        return service.queryCount(model);
    }
}
