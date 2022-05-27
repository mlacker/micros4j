package com.mlacker.micros.element.logic.builtin

import com.mlacker.micros.element.logic.ServerAction

data class EntityAction(
    var entityId: Long,
    var entityActionType: EntityActionType
) : ServerAction

