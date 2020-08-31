package com.mlacker.micros.report.controller

import com.mlacker.micros.report.api.client.DefineClient
import com.mlacker.micros.report.api.model.DefineModel
import com.mlacker.micros.report.service.DefineService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/defines")
class DefineController(private val service: DefineService): DefineClient {

    @GetMapping
    fun findAll(): Collection<DefineModel> = service.findAll()

    override fun find(id: String): DefineModel = service.find(id)
}
