package com.lacker.micros.data.api.client;

import com.lacker.micros.data.api.model.data.QueryModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient("service-data/data")
public interface ReportDataClient {

    @PostMapping("query")
    List<Map<String, Object>> query(QueryModel model);

    @PostMapping("query-count")
    Long queryCount(QueryModel model);
}
