package com.lacker.micros.data.api.client;

import com.lacker.micros.data.api.model.data.QueryModel;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReportDataClient {

    @PostMapping("query")
    List<Map<Long, Object>> query(@RequestBody QueryModel model);

    @PostMapping("query-count")
    Long queryCount(@RequestBody QueryModel model);
}
