package com.mlacker.micros.form.api.model.component

import com.mlacker.micros.form.api.model.schema.SchemaModel

class FormModel(
        override var id: Long,
        name: String,
        type: String,
        properties: String,
        children: List<ComponentModel>,
        val schema: SchemaModel
): ComponentModel(id, name, type, properties, children)
