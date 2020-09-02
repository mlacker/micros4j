package com.mlacker.micros.data.api.client;

import com.mlacker.micros.data.api.model.data.QueryModel;
import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ConditionalOnClass(name = "com.mlacker.micros.report.ReportApplication")
@FeignClient("service-data/data")
public interface ReportDataClient {

    @PostMapping("query")
    List<Map<Long, Object>> query(@RequestBody QueryModel model);

    @PostMapping("query-count")
    Long queryCount(@RequestBody QueryModel model);
}
