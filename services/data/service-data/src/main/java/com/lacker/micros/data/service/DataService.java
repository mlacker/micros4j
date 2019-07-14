package com.lacker.micros.data.service;

import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.data.QueryModel;
import com.lacker.micros.data.domain.data.DataRepository;
import com.lacker.micros.data.domain.schema.Column;
import com.lacker.micros.data.domain.schema.Table;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.domain.exception.NotFoundAppException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataService {

    private final DataRepository dataRepo;
    private final TableRepository tableRepo;
    private final SchemaTransformer schemaTransformer;

    public DataService(DataRepository dataRepo, TableRepository tableRepo, SchemaTransformer schemaTransformer) {
        this.dataRepo = dataRepo;
        this.tableRepo = tableRepo;
        this.schemaTransformer = schemaTransformer;
    }

    public List<DataModel> load(String dataId, String tableId, List<String> relationIds) {
        return null;
    }

    public void save(List<DataModel> models) {
        for (DataModel model : models) {
            Table table = tableRepo.find(model.getTableId())
                    .orElseThrow(NotFoundAppException::new);

            transformToData(table, model.getDataMaps());
        }
    }

    private void transformToData(Table table, List<Map<String, Object>> dataMaps) {
        for (Map<String, Object> dataMap : dataMaps) {
            String state = (String) dataMap.get("state");

            if (state == null) {
                String dataId = (String) dataMap.get(table.getPrimaryKey().getId());

                state = "create";
                if (dataId != null) {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("id", dataId);
                    Long count = dataRepo.queryForObject(
                            "SELECT COUNT(*) FROM table WHERE id = :id", params, Long.class);

                    if (count > 0) {
                        state = "update";
                    }
                }
            }

            for (String key : dataMap.keySet()) {
                Column column = table.getColumn(key);

                if (column == null) {
                    dataMap.remove(key);
                }
            }

            dataMap.put("state", state);
        }
    }

    public void delete(String dataId, String tableId) {

    }

    public List<Map<String, Object>> query(QueryModel model) {
        Select select = parseSqlStatement(model.getStatement());

        return dataRepo.query(select, model.getParams());
    }

    public Long queryCount(QueryModel model) {
        Select select = parseSqlStatement(model.getStatement());

        return dataRepo.queryForObject(select, model.getParams(), Long.class);
    }

    private Select parseSqlStatement(String queryStatement) {
        try {
            Statement statement = CCJSqlParserUtil.parse(queryStatement);

            if (!(statement instanceof Select)) {
                throw new UnsupportedOperationException("Only support select statement");
            }

            Limit limit = ((PlainSelect) ((Select) statement).getSelectBody()).getLimit();

            if (limit == null || ((LongValue) limit.getRowCount()).getValue() > 1000) {
                throw new UnsupportedOperationException("To protect service, only support query limit less then 1000.");
            }

            statement.accept(this.schemaTransformer);

            return (Select) statement;
        } catch (JSQLParserException ex) {
            throw new IllegalArgumentException("Invalid sql statement", ex);
        }
    }
}
