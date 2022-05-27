package com.mlacker.micros.element.logic.useraction

import com.mlacker.micros.domain.entity.EntityImpl
import com.mlacker.micros.element.Element
import com.mlacker.micros.element.logic.ServerAction
import com.mlacker.micros.element.logic.useraction.variable.Variable

class UserAction(
    id: Long,
    name: String
) : EntityImpl(id), ServerAction, Element {

    val nodes: MutableList<Node> = mutableListOf()
    val connectors: MutableList<Connector> = mutableListOf()
    val variables: MutableList<Variable> = mutableListOf()
}

