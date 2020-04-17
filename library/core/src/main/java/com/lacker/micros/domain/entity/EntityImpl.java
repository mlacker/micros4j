package com.lacker.micros.domain.entity;

import com.lacker.micros.utils.id.SequenceIdGenerator;
import java.util.Objects;

public abstract class EntityImpl implements Entity {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.id == null || getClass() != o.getClass()) {
            return false;
        }
        EntityImpl entity = (EntityImpl) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        EntityImpl clone = (EntityImpl) super.clone();
        clone.id = SequenceIdGenerator.generateId();
        return clone;
    }
}
