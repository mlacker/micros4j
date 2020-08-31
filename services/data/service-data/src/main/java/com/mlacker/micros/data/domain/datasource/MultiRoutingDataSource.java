package com.mlacker.micros.data.domain.datasource;

import com.mlacker.micros.domain.infrastructure.context.SessionContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

public class MultiRoutingDataSource extends AbstractDataSource {

    private final MultiDataSource defaultMultiDataSource;
    private final Map<String, MultiDataSource> multiDataSources;

    MultiRoutingDataSource(MultiDataSource defaultMultiDataSource, Map<String, MultiDataSource> multiDataSources) {
        this.defaultMultiDataSource = defaultMultiDataSource;
        this.multiDataSources = multiDataSources;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    private DataSource determineTargetDataSource() {
        Assert.notNull(this.multiDataSources, "DataSource router not initialized");

        String tenantId = SessionContext.getTenant();
        MultiDataSourceType type = MultiDataSourceContextHolder.getDataSource();

        DataSource dataSource = routingDataSource(tenantId, type);

        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for tenant [" + tenantId + "]");
        }

        return dataSource;
    }

    private DataSource routingDataSource(String tenantId, MultiDataSourceType type) {
        MultiDataSource multiDataSource = multiDataSources.get(tenantId);

        if (multiDataSource == null) {
            multiDataSource = defaultMultiDataSource;
        }

        if (type == MultiDataSourceType.Slaves && multiDataSource.getSlaves() != null) {
            return multiDataSource.getSlaves();
        }

        return multiDataSource.getMaster();
    }
}
