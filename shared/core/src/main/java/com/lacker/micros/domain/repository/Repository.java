package com.lacker.micros.domain.repository;

import com.lacker.micros.domain.entity.AggregateRoot;

public interface Repository<TEntity extends AggregateRoot> {

    TEntity find(String key);

    void save(TEntity entity);
}
