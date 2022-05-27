package com.mlacker.micros.element.data

import com.mlacker.micros.domain.entity.EntityImpl
import com.mlacker.micros.element.Element

class Entity(
    id: Long,
    var name: String,
    var label: String
) : EntityImpl(id), Element