package com.mlacker.micros.data.api.client;

import com.mlacker.micros.data.api.model.data.DataModel;
import com.mlacker.micros.data.api.model.form.load.LoadSchemaModel;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ConditionalOnClass(name = "com.mlacker.micros.form.FormApplication")
@FeignClient("service-data/data")
public interface FormDataClient {

    @GetMapping("load/{id}")
    List<DataModel> load(@PathVariable("id") Long id, LoadSchemaModel model);

    @PostMapping("save")
    void save(@RequestBody List<DataModel> models);

    @DeleteMapping("delete/{id}")
    void delete(@PathVariable("id") Long id, Long tableId);
}
