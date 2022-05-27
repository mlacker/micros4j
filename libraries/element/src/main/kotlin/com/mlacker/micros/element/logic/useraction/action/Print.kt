package com.mlacker.micros.element.logic.useraction.action

import com.mlacker.micros.domain.entity.EntityImpl
import com.mlacker.micros.expr.Expression

class Print(id: Long) : EntityImpl(id), Action {
    val outputs: MutableList<Expression> = mutableListOf()
}