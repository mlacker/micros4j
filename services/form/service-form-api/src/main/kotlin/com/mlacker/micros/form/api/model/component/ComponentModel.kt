package com.mlacker.micros.form.api.model.component

import com.mlacker.micros.domain.annotation.NoArg

@NoArg
open class ComponentModel(
        open val id: Long,
        val name: String,
        val type: String,
        val properties: String,
        val children: List<ComponentModel>?
)
