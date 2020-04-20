package com.lacker.micros.form.domain.define

import com.lacker.micros.domain.entity.AggregateRoot
import com.lacker.micros.domain.entity.EntityImpl

class Define(
        var name: String,
        val formId: Long
) : EntityImpl(), AggregateRoot