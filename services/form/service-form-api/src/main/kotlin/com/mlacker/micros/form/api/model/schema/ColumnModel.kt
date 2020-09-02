package com.mlacker.micros.form.api.model.schema

import com.mlacker.micros.domain.annotation.NoArg

@NoArg
data class ColumnModel(
        val id: Long,
        val name: String
)