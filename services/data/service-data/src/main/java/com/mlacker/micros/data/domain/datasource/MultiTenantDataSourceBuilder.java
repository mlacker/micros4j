package com.mlacker.micros.data.domain.datasource;

import com.mlacker.micros.data.domain.datasource.properties.DataSourceProps;
import com.mlacker.micros.data.domain.datasource.properties.DefaultPorps;
import com.mlacker.micros.data.domain.datasource.properties.MultiDataSourceProperties;
import com.mlacker.micros.data.domain.datasource.properties.TenantProps;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.util.StringUtils;

public class MultiTenantDataSourceBuilder {

    private final MultiDataSourceProperties properties;
    private final DataSourceProperties dataSourceProperties;

    public MultiTenantDataSourceBuilder(MultiDataSourceProperties properties, DataSourceProperties dataSourceProperties) {
        this.properties = properties;
        this.dataSourceProperties = dataSourceProperties;
    }

    public DataSource build() {
        MultiDataSource defaultItem;
        if (properties.getGeneral() != null && properties.getGeneral().getMaster() != null) {
            defaultItem = buildMultiDataSource(properties.getGeneral());
        } else {
            HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder()
                    .type(HikariDataSource.class).build();
            defaultItem = new MultiDataSource(dataSource);
        }

        Map<Long, MultiDataSource> multiDataSources = new HashMap<>();
        properties.getMultiTenants().forEach((key, tenantProps) -> {
            if (tenantProps != null && tenantProps.getMaster() != null) {
                multiDataSources.put(key, buildMultiDataSource(tenantProps));
            }
        });

        return new MultiRoutingDataSource(defaultItem, multiDataSources);
    }

    private MultiDataSource buildMultiDataSource(@NotNull TenantProps tenantProps) {
        MultiDataSource multiDataSource = new MultiDataSource(buildDataSource(tenantProps.getMaster()));

        if (tenantProps.getSlaves() != null) {
            multiDataSource.setSlaves(buildDataSource(tenantProps.getSlaves()));
        }

        return multiDataSource;
    }

    private DataSource buildDataSource(@NotNull DataSourceProps props) {
        DefaultPorps defaults = properties.getDefaults();
        if (defaults == null) {
            defaults = new DefaultPorps();
            defaults.setUsername(dataSourceProperties.getUsername());
            defaults.setPassword(dataSourceProperties.getPassword());
        }

        DataSourceBuilder<HikariDataSource> builder = DataSourceBuilder
                .create().type(HikariDataSource.class)
                .driverClassName(dataSourceProperties.getDriverClassName())
                .username(defaults.getUsername())
                .password(defaults.getPassword());

        if (StringUtils.hasText(props.getUrl())) {
            builder.url(props.getUrl());
        } else if (StringUtils.hasText(props.getDatabase()) && StringUtils.hasText(defaults.getUrlTemplate())) {
            String url = defaults.getUrlTemplate().replace("${database}", props.getDatabase());
            builder.url(url);
        } else {
            throw new IllegalArgumentException("url or database cannot be null");
        }
        if (StringUtils.hasText(props.getUsername())) {
            builder.username(props.getUsername());
        }
        if (StringUtils.hasText(props.getPassword())) {
            builder.password(props.getPassword());
        }

        return builder.build();
    }
}
