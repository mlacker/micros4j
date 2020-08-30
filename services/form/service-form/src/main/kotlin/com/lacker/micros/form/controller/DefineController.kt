package com.lacker.micros.form.controller

import com.mlacker.micros.domain.entity.PaginatedFilter
import com.lacker.micros.form.api.model.define.DefineModel
import com.lacker.micros.form.service.DefineService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/defines")
class DefineController(
        private val service: DefineService
) {
    @GetMapping(params = ["page", "size"])
    fun findByFilter(filter: PaginatedFilter) = service.findByFilter(filter)

    @GetMapping
    fun findAll() = service.findAll()

    @GetMapping("{id}")
    fun find(@PathVariable id: Long) = service.find(id)

    @PostMapping
    fun save(@RequestBody model: DefineModel) = service.save(model)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)
}