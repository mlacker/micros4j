package com.mlacker.micros.expr.types

import com.mlacker.micros.domain.annotation.NoArg
import com.mlacker.micros.expr.Type

@NoArg
class ListType(
    val innerType: Type
) : Type
