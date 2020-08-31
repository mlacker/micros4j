package com.mlacker.micros.data.domain.datasource;

import javax.sql.DataSource;

class MultiDataSource {

    private DataSource master;
    private DataSource slaves;

    MultiDataSource(DataSource master) {
        this.master = master;
    }

    DataSource getMaster() {
        return master;
    }

    DataSource getSlaves() {
        return slaves;
    }

    void setSlaves(DataSource slaves) {
        this.slaves = slaves;
    }
}
