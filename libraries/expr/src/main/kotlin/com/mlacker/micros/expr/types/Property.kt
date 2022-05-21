package com.mlacker.micros.expr.types

import com.mlacker.micros.domain.annotation.NoArg
import com.mlacker.micros.expr.Type

@NoArg
class Property(
    val id: Long,
    val name: String,
    val description: String?,
    val dataType: Type,
    val mandatory: Boolean?,
    val length: Int? = null,
    val decimals: Int? = null,
    val defaultValue: Any? = null
)
