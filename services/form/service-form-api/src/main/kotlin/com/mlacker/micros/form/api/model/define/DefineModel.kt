package com.mlacker.micros.form.api.model.define

import com.mlacker.micros.form.api.model.component.FormModel

data class DefineModel(
        val id: Long,
        val name: String,
        val form: FormModel
)
