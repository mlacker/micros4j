package com.mlacker.micros.expr.types

import com.mlacker.micros.domain.annotation.NoArg
import com.mlacker.micros.expr.Type

@NoArg
class RecordType(
    val id: Long,
    val properties: List<RecordProperty>,
) : Type {
    var name: String? = null
        get() {
            return field ?: "_$id"
        }
}

@NoArg
class RecordProperty(
    val name: String,
    val dataType: Type
)
