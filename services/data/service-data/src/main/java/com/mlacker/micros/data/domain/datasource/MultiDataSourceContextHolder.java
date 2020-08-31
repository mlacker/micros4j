package com.mlacker.micros.data.domain.datasource;

class MultiDataSourceContextHolder {

    private static final ThreadLocal<MultiDataSourceType> contextHolder = new ThreadLocal<>();

    static void setDataSource(MultiDataSourceType dataSource) {
        contextHolder.set(dataSource);
    }

    static MultiDataSourceType getDataSource() {
        return contextHolder.get();
    }

    static void reset() {
        contextHolder.remove();
    }
}
