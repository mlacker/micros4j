package com.mlacker.micros.form.api.model.component

open class ComponentModel(
        val id: Long,
        val name: String,
        val type: String,
        val properties: String,
        val children: List<ComponentModel>?
)
