package com.mlacker.micros.report.api.client

import com.mlacker.micros.report.api.model.DefineModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("service-report/defines")
interface DefineClient {

    @GetMapping("{id}")
    fun find(@PathVariable id: String): DefineModel
}