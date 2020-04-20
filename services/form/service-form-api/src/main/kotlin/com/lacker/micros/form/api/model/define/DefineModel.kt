package com.lacker.micros.form.api.model.define

import com.lacker.micros.form.api.model.component.FormModel

data class DefineModel(
        val id: Long,
        val name: String,
        val form: FormModel
)
