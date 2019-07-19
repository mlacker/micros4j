package com.lacker.micros.data.api.client;

import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.schema.SchemaModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("service-data/data")
public interface FormDataClient {

    @GetMapping("load/{id}")
    List<DataModel> load(@PathVariable String id, SchemaModel model);

    @PostMapping("save")
    void save(@RequestBody List<DataModel> models);

    @DeleteMapping("delete/{id}")
    void delete(@PathVariable String id, String tableId);
}
