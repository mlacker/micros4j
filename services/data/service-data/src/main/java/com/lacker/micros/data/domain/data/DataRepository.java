package com.lacker.micros.data.domain.data;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

import java.util.List;
import java.util.Map;

public interface DataRepository {

    List<Map<String, Object>> query(Select select, List<Object> params);

    <T> T queryForObject(Select select, List<Object> params, Class<T> requiredType);

    int update(Statement statement, List<Object> params);

    void execute(Statement statement);
}
