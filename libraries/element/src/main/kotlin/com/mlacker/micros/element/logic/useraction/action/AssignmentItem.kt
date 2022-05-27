package com.mlacker.micros.element.logic.useraction.action

import com.mlacker.micros.expr.Expression

data class AssignmentItem(
    // TODO: What's type should be here?
    var variable: Any,
    var value: Expression
)