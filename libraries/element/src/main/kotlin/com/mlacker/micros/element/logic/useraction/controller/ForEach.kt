package com.mlacker.micros.element.logic.useraction.controller

import com.mlacker.micros.expr.Expression

class ForEach(
    id: Long,
    var recordList: Expression,
    var cycleConnector: Long,
    var endConnector: Long
)