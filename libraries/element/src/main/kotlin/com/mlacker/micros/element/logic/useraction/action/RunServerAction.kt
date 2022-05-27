package com.mlacker.micros.element.logic.useraction.action

import com.mlacker.micros.domain.entity.EntityImpl
import com.mlacker.micros.element.logic.ServerAction

class RunServerAction(
    id: Long,
    var serverAction: ServerAction,
    var parameters: List<ActionParameter>
) : EntityImpl(id), Action