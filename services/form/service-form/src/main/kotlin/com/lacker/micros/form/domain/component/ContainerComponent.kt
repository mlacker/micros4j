package com.lacker.micros.form.domain.component

open class ContainerComponent(
        id: Long, name: String, type: String, properties: String
) : Component(id, name, type, properties) {

    var children: MutableList<Component> = mutableListOf()
        private set

    override fun clone(): ContainerComponent =
            (super.clone() as ContainerComponent).also {
                it.children = children.map { it.clone() }.toMutableList()
            }
}