package com.mlacker.micros.data.domain.datasource;

import com.mlacker.micros.domain.infrastructure.context.PrincipalHolder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

public class MultiRoutingDataSource extends AbstractDataSource {

    private final MultiDataSource defaultMultiDataSource;
    private final Map<Long, MultiDataSource> multiDataSources;

    MultiRoutingDataSource(MultiDataSource defaultMultiDataSource, Map<Long, MultiDataSource> multiDataSources) {
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

        var applicationId = PrincipalHolder.getContext().getApplicationId();
        MultiDataSourceType type = MultiDataSourceContextHolder.getDataSource();

        DataSource dataSource = routingDataSource(applicationId, type);

        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for application [" + applicationId + "]");
        }

        return dataSource;
    }

    private DataSource routingDataSource(Long applicationId, MultiDataSourceType type) {
        MultiDataSource multiDataSource = multiDataSources.get(applicationId);

        if (multiDataSource == null) {
            multiDataSource = defaultMultiDataSource;
        }

        if (type == MultiDataSourceType.Slaves && multiDataSource.getSlaves() != null) {
            return multiDataSource.getSlaves();
        }

        return multiDataSource.getMaster();
    }
}
