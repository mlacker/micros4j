package com.mlacker.micros.element.data

import com.mlacker.micros.domain.entity.EntityImpl
import com.mlacker.micros.element.Element
import com.mlacker.micros.element.type.BasicDataType

class EntityProperty(
    id: Long,
    var name: String,
    var label: String?,
    var dataType: BasicDataType,
    var length: Int?,
    var decimals: Int?,
    val isAutoNumber: Boolean = false,
    val isMandatory: Boolean = false,
    var defaultValue: Any? = null
) : EntityImpl(id), Element