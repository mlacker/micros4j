package com.lacker.micros.data.api.client;

import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.data.QueryModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-data/data")
public interface ReportDataClient {

    @PostMapping("query")
    DataModel query(QueryModel model);
}
