package com.lacker.micros.domain.repository;

import com.lacker.micros.domain.entity.AggregateRoot;

import java.util.Optional;

public interface Repository<TEntity extends AggregateRoot> {

    TEntity find(Long id);

    void save(TEntity entity);
}
