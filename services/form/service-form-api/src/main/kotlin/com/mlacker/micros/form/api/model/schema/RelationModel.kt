package com.mlacker.micros.form.api.model.schema

import com.mlacker.micros.domain.annotation.NoArg

@NoArg
data class RelationModel(
        val id: Long,
        val primaryTable: Long,
        val primaryColumn: Long,
        val foreignTable: Long,
        val foreignColumn: Long,
        val multiple: Boolean
)