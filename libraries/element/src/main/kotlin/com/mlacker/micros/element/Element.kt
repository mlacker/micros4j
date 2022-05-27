package com.mlacker.micros.element

import com.mlacker.micros.domain.entity.Entity

interface Element : Entity {
    val type: String
        get() = this::class.simpleName!!
}