package com.mlacker.micros.data.domain.data;

import com.mlacker.micros.data.domain.statement.ParameterStatement;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.statement.Statement;

public interface DataRepository {

    List<Map<Long, Object>> query(ParameterStatement statement);

    <T> T queryForObject(ParameterStatement statement, Class<T> requiredType);

    int update(ParameterStatement statement);

    void execute(Statement statement);
}
