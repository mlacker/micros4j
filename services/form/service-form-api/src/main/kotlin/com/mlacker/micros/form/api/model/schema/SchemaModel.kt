package com.mlacker.micros.form.api.model.schema

data class SchemaModel(
        val tables: TableModel,
        val relations: RelationModel
)

data class TableModel(
        val id: Long,
        val name: String,
        val columns: ColumnModel
)

data class ColumnModel(
        val id: Long,
        val name: String
)

data class RelationModel(
        val id: Long,
        val primaryTable: Long,
        val primaryColumn: Long,
        val foreignTable: Long,
        val foreignColumn: Long,
        val multiple: Boolean
)
