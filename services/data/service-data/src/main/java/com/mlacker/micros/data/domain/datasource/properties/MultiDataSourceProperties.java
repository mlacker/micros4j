package com.mlacker.micros.data.domain.datasource.properties;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("micros.datasource")
public class MultiDataSourceProperties {

    private DefaultPorps defaults;
    private TenantProps general;
    private Map<Long, TenantProps> multiTenants = new HashMap<>();

    public DefaultPorps getDefaults() {
        return defaults;
    }

    public void setDefaults(DefaultPorps defaults) {
        this.defaults = defaults;
    }

    public TenantProps getGeneral() {
        return general;
    }

    public void setGeneral(TenantProps general) {
        this.general = general;
    }

    public Map<Long, TenantProps> getMultiTenants() {
        return multiTenants;
    }

    public void setMultiTenants(Map<Long, TenantProps> multiTenants) {
        this.multiTenants = multiTenants;
    }

}
