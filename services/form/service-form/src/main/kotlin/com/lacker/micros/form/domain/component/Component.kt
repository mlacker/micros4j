package com.lacker.micros.form.domain.component

import com.mlacker.micros.domain.entity.EntityImpl

open class Component(
        id: Long,
        var name: String,
        val type: String,
        var properties: String,
        // val archive: string,
) : EntityImpl(id), Cloneable {

    var parentId: Long = 0

    public override fun clone() = super<EntityImpl>.clone() as Component
}

