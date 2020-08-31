package com.mlacker.micros.form.domain.define

import com.mlacker.micros.domain.entity.AggregateRoot
import com.mlacker.micros.domain.entity.EntityImpl

class Define(
        id: Long,
        var name: String,
        val formId: Long
) : EntityImpl(id), AggregateRoot