package com.mlacker.micros.form.api.model.schema

import com.mlacker.micros.domain.annotation.NoArg

@NoArg
data class SchemaModel(
        val tables: List<TableModel>,
        val relations: List<RelationModel>
)

