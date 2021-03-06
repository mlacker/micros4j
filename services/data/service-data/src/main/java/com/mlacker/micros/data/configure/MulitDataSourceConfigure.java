package com.mlacker.micros.data.configure;

import com.mlacker.micros.data.domain.datasource.MultiTenantDataSourceBuilder;
import com.mlacker.micros.data.domain.datasource.properties.MultiDataSourceProperties;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MultiDataSourceProperties.class)
public class MulitDataSourceConfigure {

    @Bean
    public DataSource dataSource(DataSourceProperties properties, MultiDataSourceProperties multiProperties) {
        MultiTenantDataSourceBuilder builder = new MultiTenantDataSourceBuilder(multiProperties, properties);

        return builder.build();
    }
}
