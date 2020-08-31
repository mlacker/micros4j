package com.mlacker.micros.data.domain.datasource.properties;

public class TenantProps {
    private DataSourceProps master;
    private DataSourceProps slaves;

    public DataSourceProps getMaster() {
        return master;
    }

    public void setMaster(DataSourceProps master) {
        this.master = master;
    }

    public DataSourceProps getSlaves() {
        return slaves;
    }

    public void setSlaves(DataSourceProps slaves) {
        this.slaves = slaves;
    }
}
