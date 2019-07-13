package com.lacker.micros.data.controller;

import com.lacker.micros.data.api.client.FormDataClient;
import com.lacker.micros.data.api.client.ReportDataClient;
import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.data.QueryModel;
import com.lacker.micros.data.service.DataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController implements FormDataClient, ReportDataClient {

    private final DataService service;

    public DataController(DataService service) {
        this.service = service;
    }

    @Override
    public List<DataModel> load(String id, String tableId, List<String> relationIds) {
        return null;
    }

    @Override
    public void save(List<DataModel> models) {

    }

    @Override
    public void delete(String id, String tableId) {

    }

    @Override
    public DataModel query(QueryModel model) {
        return service.query(model);
    }
}
