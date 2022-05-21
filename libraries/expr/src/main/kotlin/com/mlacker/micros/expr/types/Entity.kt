package com.mlacker.micros.expr.types

import com.mlacker.micros.domain.annotation.NoArg

@NoArg
class Entity(
    var id: Long,
    var name: String,
    var description: String?,
    var properties: List<Property>
)
