package com.mlacker.micros.element.logic.useraction.variable

import com.mlacker.micros.domain.entity.EntityImpl
import com.mlacker.micros.element.type.DataType

class InputParameter(
    id: Long,
    override var name: String,
    override var dataType: DataType,
    var mandatory: Boolean
) : EntityImpl(id), Variable