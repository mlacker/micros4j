package com.mlacker.micros.domain.entity

import com.mlacker.micros.domain.annotation.NoArg
import com.mlacker.micros.utils.id.SequenceIdGenerator.Companion.generateId
import java.util.*

@NoArg
abstract class EntityImpl (id: Long) : Entity, Cloneable {
    final override var id: Long = id
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        return id == (other as Entity).id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun clone(): Any {
        val clone = super.clone() as EntityImpl
        clone.id = generateId()
        return clone
    }
}