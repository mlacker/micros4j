package com.lacker.micros.data.api.client;

import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.form.load.LoadSchemaModel;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FormDataClient {

    @GetMapping("load/{id}")
    List<DataModel> load(@PathVariable Long id, LoadSchemaModel model);

    @PostMapping("save")
    void save(@RequestBody List<DataModel> models);

    @DeleteMapping("delete/{id}")
    void delete(@PathVariable Long id, Long tableId);
}
