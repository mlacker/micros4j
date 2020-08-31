package com.mlacker.micros.data.controller;

import com.mlacker.micros.data.api.client.FormDataClient;
import com.mlacker.micros.data.api.client.ReportDataClient;
import com.mlacker.micros.data.api.model.data.DataModel;
import com.mlacker.micros.data.api.model.data.QueryModel;
import com.mlacker.micros.data.api.model.form.load.LoadSchemaModel;
import com.mlacker.micros.data.service.DataService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController implements FormDataClient, ReportDataClient {

    private final DataService service;

    public DataController(DataService service) {
        this.service = service;
    }

    @Override
    public List<DataModel> load(Long id, LoadSchemaModel model) {
        return service.load(id, model);
    }

    @Override
    public void save(List<DataModel> models) {
        service.save(models);
    }

    @Override
    public void delete(Long id, Long tableId) {
        service.delete(id, tableId);
    }

    @Override
    public List<Map<Long, Object>> query(QueryModel model) {
        return service.query(model);
    }

    @Override
    public Long queryCount(QueryModel model) {
        return service.queryCount(model);
    }
}
