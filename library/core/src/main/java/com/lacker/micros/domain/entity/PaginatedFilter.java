package com.lacker.micros.domain.entity;

import java.util.HashMap;
import java.util.Map;

public class PaginatedFilter {

    private int page;
    private int size;
    private String sort;
    private String order;
    private Map<String, String> filters;

    public PaginatedFilter() {
        page = 0;
        size = 10;
        filters = new HashMap<>();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page > 0 ? page - 1 : page;
    }

    public int getStart() {
        return getPage() * getSize();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public boolean containsFilter(String key) {
        return filters.containsKey(key);
    }

    public String getFilter(String key) {
        return filters.get(key);
    }

    public void setFilter(String key, String value) {
        filters.put(key, value);
    }
}
