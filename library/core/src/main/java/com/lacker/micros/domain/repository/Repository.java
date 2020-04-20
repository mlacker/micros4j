package com.lacker.micros.domain.repository;

import com.lacker.micros.domain.entity.AggregateRoot;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface Repository<TEntity extends AggregateRoot> {

    TEntity find(@NotNull Long id);

    void save(@NotNull TEntity entity);
}
