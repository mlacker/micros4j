package com.mlacker.micros.element.logic.useraction.controller

import com.mlacker.micros.domain.entity.EntityImpl
import com.mlacker.micros.expr.Expression

class If(
    id: Long,
    var condition: Expression,
    var trueConnector: Long,
    var falseConnector: Long,
) : EntityImpl(id), Controller