package com.lacker.micros.domain.entity;

import java.util.Objects;

public abstract class EntityImpl implements Entity {

    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
