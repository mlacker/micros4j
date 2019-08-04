package com.lacker.micros.data.service;

import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.data.QueryModel;
import com.lacker.micros.data.api.model.form.load.LoadSchemaModel;
import com.lacker.micros.data.domain.data.DataRepository;
import com.lacker.micros.data.domain.schema.DataColumn;
import com.lacker.micros.data.domain.schema.DataTable;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.data.domain.statement.ParameterStatement;
import com.lacker.micros.data.service.statement.SchemaTransformer;
import com.lacker.micros.data.service.statement.builder.FormStatementBuilder;
import com.lacker.micros.domain.exception.NotFoundAppException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataService {

    private final DataRepository dataRepo;
    private final TableRepository tableRepo;
    private final SchemaTransformer schemaTransformer;
    private final FormStatementBuilder formStatementBuilder;

    public DataService(DataRepository dataRepo, TableRepository tableRepo, SchemaTransformer schemaTransformer, FormStatementBuilder formStatementBuilder) {
        this.dataRepo = dataRepo;
        this.tableRepo = tableRepo;
        this.schemaTransformer = schemaTransformer;
        this.formStatementBuilder = formStatementBuilder;
    }

    public List<DataModel> load(String dataId, LoadSchemaModel model) {
        List<DataModel> dataModels = new ArrayList<>();

        DataModel primaryModel = loadForTable(model.getPrimary(), null, dataId);
        if (primaryModel.getDataMaps().size() == 0) {
            throw new NotFoundAppException();
        }
        dataModels.add(primaryModel);

        for (LoadSchemaModel.LoadRelationModel relationModel : model.getRelations()) {
            DataModel dataModel = loadForTable(
                    relationModel.getForeignTable(), relationModel.getForeignColumn(), primaryModel.getDataMaps().get(0).get(relationModel.getPrimaryColumn()));
            dataModels.add(dataModel);
        }

        return dataModels;
    }

    private DataModel loadForTable(LoadSchemaModel.LoadTableModel tableModel, String conditionColumn, Object conditionValue) {
        DataTable table = tableRepo.findOne(tableModel.getId());
        ParameterStatement statement = formStatementBuilder.select(table, tableModel.getIncludeColumns(), table.getColumn(conditionColumn), conditionValue);
        List<Map<String, Object>> dataMaps = dataRepo.query((Select) statement.getStatement(), statement.getParameters());

        DataModel dataModel = new DataModel();
        dataModel.setTableId(table.getId());
        dataModel.setDataMaps(dataMaps);
        return dataModel;
    }

    @Transactional
    public void save(List<DataModel> models) {
        List<ParameterStatement> statements = new ArrayList<>();
        for (DataModel model : models) {
            DataTable table = tableRepo.findOne(model.getTableId());

            statements.addAll(saveForTable(table, model.getIncludeColumns(), model.getConditions(), model.getDataMaps()));
        }

        for (ParameterStatement statement : statements) {
            dataRepo.update(statement.getStatement(), statement.getParameters());
        }
    }

    private List<String> fetchDatabase(DataTable table, Map<String, List<String>> conditions) {
        ParameterStatement statement = formStatementBuilder.selectIn(table, conditions);

        return dataRepo.query((Select) statement.getStatement(), statement.getParameters()).stream()
                .map(m -> String.valueOf(m.get(table.getPrimaryKey().getId())))
                .collect(Collectors.toList());
    }

    private List<ParameterStatement> saveForTable(DataTable table, List<String> includeColumns, Map<String, List<String>> conditions, List<Map<String, Object>> dataMaps) {
        DataColumn primaryKey = table.getPrimaryKey();

        List<String> persistIds = fetchDatabase(table, conditions);

        List<Map<String, Object>> insertRows = new ArrayList<>();
        List<Map<String, Object>> updateRows = new ArrayList<>();
        for (Map<String, Object> dataMap : dataMaps) {
            String id = String.valueOf(dataMap.get(primaryKey.getId()));

            if (persistIds.contains(id)) {
                updateRows.add(dataMap);
                persistIds.remove(id);
            } else {
                insertRows.add(dataMap);
            }
        }

        List<ParameterStatement> statements = new ArrayList<>();
        statements.add(formStatementBuilder.insert(table, includeColumns, insertRows));
        statements.addAll(formStatementBuilder.update(table, includeColumns, updateRows));
        statements.add(formStatementBuilder.delete(table, persistIds));
        return statements;
    }

    public void delete(String dataId, String tableId) {
        DataTable table = tableRepo.findOne(tableId);

        formStatementBuilder.delete(table, dataId);
    }

    public List<Map<String, Object>> query(QueryModel model) {
        Select select = parseSqlStatement(model.getStatement());

        return dataRepo.query(select, model.getParameters());
    }

    public Long queryCount(QueryModel model) {
        Select select = parseSqlStatement(model.getStatement());

        return dataRepo.queryForObject(select, model.getParameters(), Long.class);
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
