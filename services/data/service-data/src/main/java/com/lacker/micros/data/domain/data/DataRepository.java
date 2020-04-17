package com.lacker.micros.data.domain.data;

import com.lacker.micros.data.domain.statement.ParameterStatement;
import net.sf.jsqlparser.statement.Statement;

import java.util.List;
import java.util.Map;

public interface DataRepository {

    List<Map<Long, Object>> query(ParameterStatement statement);

    <T> T queryForObject(ParameterStatement statement, Class<T> requiredType);

    int update(ParameterStatement statement);

    void execute(Statement statement);
}
