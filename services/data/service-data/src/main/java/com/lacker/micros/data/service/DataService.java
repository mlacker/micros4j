package com.lacker.micros.data.service;

import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.data.QueryModel;
import com.lacker.micros.data.api.model.form.load.LoadRelationModel;
import com.lacker.micros.data.api.model.form.load.LoadSchemaModel;
import com.lacker.micros.data.domain.data.DataRepository;
import com.lacker.micros.data.domain.schema.DataColumn;
import com.lacker.micros.data.domain.schema.DataTable;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.data.domain.statement.ParameterStatement;
import com.lacker.micros.data.service.statement.StetementTransformer;
import com.lacker.micros.data.service.statement.StatementBuilder;
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

    private final StatementBuilder statementBuilder;
    private final StetementTransformer schemaTransformer;
    private final DataRepository dataRepo;
    private final TableRepository tableRepo;

    public DataService(
            StatementBuilder statementBuilder, StetementTransformer schemaTransformer, DataRepository dataRepo, TableRepository tableRepo) {
        this.statementBuilder = statementBuilder;
        this.schemaTransformer = schemaTransformer;
        this.dataRepo = dataRepo;
        this.tableRepo = tableRepo;
    }

    public List<DataModel> load(String dataId, LoadSchemaModel model) {
        List<DataModel> dataModels = new ArrayList<>();

        DataTable tableOfPrimary = tableRepo.findOne(model.getPrimary().getId());
        DataModel dataModelOfPrimary = loadForTable(tableOfPrimary, model.getPrimary().getIncludeColumns(), tableOfPrimary.getPrimaryKey(), dataId);
        if (dataModelOfPrimary.getDataMaps().size() == 0) {
            throw new NotFoundAppException();
        }
        dataModels.add(dataModelOfPrimary);

        for (LoadRelationModel relationModel : model.getRelations()) {
            DataTable table = tableRepo.findOne(relationModel.getForeignTable().getId());
            DataModel dataModel = loadForTable(
                    table,
                    relationModel.getForeignTable().getIncludeColumns(),
                    table.getColumn(relationModel.getForeignColumn()),
                    dataModelOfPrimary.getDataMaps().get(0).get(relationModel.getPrimaryColumn()));
            dataModels.add(dataModel);
        }

        return dataModels;
    }

    @Transactional
    public void save(List<DataModel> models) {
        List<ParameterStatement> statements = new ArrayList<>();
        for (DataModel model : models) {
            statements.addAll(saveForTable(model));
        }

        for (ParameterStatement statement : statements) {
            dataRepo.update(statement);
        }
    }

    public void delete(String dataId, String tableId) {
        DataTable table = tableRepo.findOne(tableId);

        statementBuilder.delete(table, dataId);
    }

    public List<Map<String, Object>> query(QueryModel model) {
        Select select = parseSqlStatement(model.getStatement());

        return dataRepo.query(new ParameterStatement(select, model.getParameters()));
    }

    public Long queryCount(QueryModel model) {
        Select select = parseSqlStatement(model.getStatement());

        return dataRepo.queryForObject(new ParameterStatement(select, model.getParameters()), Long.class);
    }

    private Select parseSqlStatement(String queryStatement) {
        try {
            Statement statement = CCJSqlParserUtil.parse(queryStatement);

            if (!(statement instanceof Select)) {
                throw new UnsupportedOperationException("Only support select statement");
            }

            Select select = (Select) statement;

            Limit limit = ((PlainSelect) (select).getSelectBody()).getLimit();

            if (limit == null || ((LongValue) limit.getRowCount()).getValue() > 1000) {
                throw new UnsupportedOperationException("To protect service, only support query limit less then 1000.");
            }

            select.accept(this.schemaTransformer);

            return select;
        } catch (JSQLParserException ex) {
            throw new IllegalArgumentException("Invalid sql statement", ex);
        }
    }

    private DataModel loadForTable(DataTable table, List<String> includeColumns, DataColumn conditionColumn, Object conditionValue) {
        ParameterStatement statement = statementBuilder.select(table, includeColumns, conditionColumn, conditionValue);
        List<Map<String, Object>> dataMaps = dataRepo.query(statement);

        DataModel dataModel = new DataModel();
        dataModel.setTableId(table.getId());
        dataModel.setDataMaps(dataMaps);
        return dataModel;
    }

    private List<ParameterStatement> saveForTable(DataModel model) {
        DataTable table = tableRepo.findOne(model.getTableId());
        List<String> persistIds = fetchDatabase(table, model.getConditions());

        List<Map<String, Object>> insertRows = new ArrayList<>();
        List<Map<String, Object>> updateRows = new ArrayList<>();
        for (Map<String, Object> dataMap : model.getDataMaps()) {
            String id = String.valueOf(dataMap.get(table.getPrimaryKey().getId()));

            if (persistIds.contains(id)) {
                updateRows.add(dataMap);
                persistIds.remove(id);
            } else {
                insertRows.add(dataMap);
            }
        }

        List<ParameterStatement> statements = new ArrayList<>();
        statements.add(statementBuilder.insert(table, model.getIncludeColumns(), insertRows));
        statements.addAll(statementBuilder.update(table, model.getIncludeColumns(), updateRows));
        statements.add(statementBuilder.delete(table, persistIds));
        return statements;
    }

    private List<String> fetchDatabase(DataTable table, Map<String, List<String>> conditions) {
        ParameterStatement statement = statementBuilder.selectIn(table, conditions);

        return dataRepo.query(statement).stream()
                .map(m -> String.valueOf(m.get(table.getPrimaryKey().getId())))
                .collect(Collectors.toList());
    }
}
