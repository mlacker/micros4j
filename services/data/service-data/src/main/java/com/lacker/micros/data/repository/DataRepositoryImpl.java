package com.lacker.micros.data.repository;

import com.lacker.micros.data.domain.data.DataRepository;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class DataRepositoryImpl implements DataRepository {

    private final JdbcTemplate template;

    public DataRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Map<String, Object>> query(Select select, List<Object> params) {
        return template.queryForList(select.toString(), params);
    }

    @Override
    public <T> T queryForObject(Select select, List<Object> params, Class<T> requiredType) {
        return template.queryForObject(select.toString(), params.toArray(), requiredType);
    }

    @Override
    public int update(Statement statement, List<Object> params) {
        return template.update(statement.toString(), params);
    }

    @Override
    public void execute(Statement statement) {
        template.execute(statement.toString());
    }
}
