package com.lacker.micros.data.api.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("service-data/data")
public interface DataClient extends FormDataClient, ReportDataClient {
}
