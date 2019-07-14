package com.lacker.micros.data.domain.data;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

import java.util.List;
import java.util.Map;

public interface DataRepository {

    List<Map<String, Object>> query(Select select, Map<String, Object> params);

    Long queryForObject(Select select, Map<String, Object> params, Class<Long> requiredType);

    int update(Statement statement, Map<String, Object> params);

    void execute(Statement statement);
}
