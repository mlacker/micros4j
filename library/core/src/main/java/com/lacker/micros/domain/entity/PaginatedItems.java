package com.lacker.micros.domain.entity;

import java.util.List;

public class PaginatedItems<TEntity> {

    private List<TEntity> items;
    private long total;

    public PaginatedItems() {
    }

    public PaginatedItems(List<TEntity> items, long total) {
        if (items == null) {
            throw new IllegalArgumentException("items cannot be null.");
        }

        this.items = items;
        this.total = total;
    }

    public List<TEntity> getItems() {
        return items;
    }

    public long getTotal() {
        return total;
    }
}
