package com.mlacker.micros.data.repository;

import com.mlacker.micros.data.domain.data.DataRepository;
import com.mlacker.micros.data.domain.statement.ParameterStatement;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataRepositoryImpl implements DataRepository {

    private final JdbcTemplate template;

    public DataRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Map<Long, Object>> query(ParameterStatement statement) {
        throw new UnsupportedOperationException("Not Implement");
        // return template.queryForList(statement.getStatement().toString(), statement.getParameters());
    }

    @Override
    public <T> T queryForObject(ParameterStatement statement, Class<T> requiredType) {
        return template.queryForObject(statement.getStatement().toString(), statement.getParameters(), requiredType);
    }

    @Override
    public int update(ParameterStatement statement) {
        return template.update(statement.toString(), statement.getParameters());
    }

    @Override
    public void execute(Statement statement) {
        template.execute(statement.toString());
    }
}
