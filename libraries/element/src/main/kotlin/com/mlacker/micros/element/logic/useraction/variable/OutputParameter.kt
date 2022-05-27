package com.mlacker.micros.element.logic.useraction.variable

import com.mlacker.micros.domain.entity.EntityImpl
import com.mlacker.micros.element.type.DataType

class OutputParameter(id: Long, override var name: String, override var dataType: DataType) : EntityImpl(id), Variable