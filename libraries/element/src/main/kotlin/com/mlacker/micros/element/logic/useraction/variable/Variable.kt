package com.mlacker.micros.element.logic.useraction.variable

import com.mlacker.micros.element.Element
import com.mlacker.micros.element.type.DataType

interface Variable : Element {
    val name: String
    val dataType: DataType
}

