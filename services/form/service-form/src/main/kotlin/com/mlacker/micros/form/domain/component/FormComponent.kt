package com.mlacker.micros.form.domain.component

import com.mlacker.micros.domain.entity.AggregateRoot

class FormComponent(
        id: Long, name: String, type: String, properties: String
) : ContainerComponent(id, name, type, properties), AggregateRoot {
    val schema: Schema = Schema()
}