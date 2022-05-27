package com.mlacker.micros.element.logic.useraction.action

import com.mlacker.micros.domain.entity.EntityImpl

class Assign(id: Long) : EntityImpl(id), Action {
    val assignments: MutableList<AssignmentItem> = mutableListOf()
}