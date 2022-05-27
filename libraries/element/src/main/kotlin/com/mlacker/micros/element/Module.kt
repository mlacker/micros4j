package com.mlacker.micros.element

import com.mlacker.micros.domain.entity.AggregateRoot
import com.mlacker.micros.domain.entity.EntityImpl
import java.time.LocalDateTime

class Module(
    id: Long,
    var name: String,
    val applicationId: Long,
    val creationUser: Long
) : EntityImpl(id), Element, AggregateRoot {

    val elements: MutableList<Element> = mutableListOf()
    val creationTime: LocalDateTime = LocalDateTime.now()
}