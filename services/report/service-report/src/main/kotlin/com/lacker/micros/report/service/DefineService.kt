package com.lacker.micros.report.service

import com.lacker.micros.report.api.model.DefineModel
import org.springframework.stereotype.Service

@Service
class DefineService {

    fun findAll(): Collection<DefineModel> {
        TODO("Not yet implemented")
    }

    fun find(id: String): DefineModel {
        return DefineModel(1, "report-1")
    }
}
