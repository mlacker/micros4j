package com.mlacker.micros.expr.types

import com.mlacker.micros.domain.annotation.NoArg

@NoArg
class Structure(
    var id: Long,
    var name: String,
    var description: String?,
    var createdDate: String,
    var lastModifiedDate: String,
    var properties: List<Property>
)
