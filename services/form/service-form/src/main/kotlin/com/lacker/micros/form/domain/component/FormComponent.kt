package com.lacker.micros.form.domain.component

import com.mlacker.micros.domain.entity.AggregateRoot

class FormComponent(
        name: String, properties: String
) : ContainerComponent(name, "rc-form", properties), AggregateRoot