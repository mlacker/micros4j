package com.lacker.micros.data.repository;

import com.lacker.micros.data.domain.data.DataRepository;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class DataRepositoryImpl implements DataRepository {

    private final NamedParameterJdbcTemplate template;

    public DataRepositoryImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Map<String, Object>> query(Select select, Map<String, Object> params) {
        return template.queryForList(select.toString(), params);
    }

    @Override
    public Long queryForObject(Select select, Map<String, Object> params, Class<Long> requiredType) {
        return template.queryForObject(select.toString(), params, requiredType);
    }

    @Override
    public int update(Statement statement, Map<String, Object> params) {
        return template.update(statement.toString(), params);
    }

    @Override
    public void execute(Statement statement) {
        template.getJdbcTemplate().execute(statement.toString());
    }
}
