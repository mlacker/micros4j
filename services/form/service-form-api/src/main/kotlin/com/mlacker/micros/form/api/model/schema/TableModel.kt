package com.mlacker.micros.form.api.model.schema

import com.mlacker.micros.domain.annotation.NoArg

@NoArg
data class TableModel(
        val id: Long,
        val name: String,
        val columns: List<ColumnModel>
)