package com.mlacker.micros.element.logic.builtin.list

import com.mlacker.micros.element.logic.ServerAction
import com.mlacker.micros.expr.Expression

data class ListAppend(
    var list: Expression,
    var element: Expression
) : ServerAction