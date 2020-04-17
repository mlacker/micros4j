package com.lacker.micros.data.service;

import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.data.QueryModel;
import com.lacker.micros.data.api.model.form.load.LoadRelationModel;
import com.lacker.micros.data.api.model.form.load.LoadSchemaModel;
import com.lacker.micros.data.domain.data.DataRepository;
import com.lacker.micros.data.domain.datasource.DataSource;
import com.lacker.micros.data.domain.datasource.MultiDataSourceType;
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

    @DataSource(MultiDataSourceType.Slaves)
    public List<DataModel> load(Long dataId, LoadSchemaModel model) {
        List<DataModel> dataModels = new ArrayList<>();

        DataTable tableOfPrimary = tableRepo.find(model.getPrimary().getId());
        DataModel dataModelOfPrimary = loadForTable(tableOfPrimary, model.getPrimary().getIncludeColumns(), tableOfPrimary.getPrimaryKey(), dataId);
        if (dataModelOfPrimary.getDataMaps().size() == 0) {
            throw new NotFoundAppException();
        }
        dataModels.add(dataModelOfPrimary);

        for (LoadRelationModel relationModel : model.getRelations()) {
            DataTable table = tableRepo.find(relationModel.getForeignTable().getId());
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
    @DataSource(MultiDataSourceType.Master)
    public void save(List<DataModel> models) {
        List<ParameterStatement> statements = new ArrayList<>();
        for (DataModel model : models) {
            statements.addAll(saveForTable(model));
        }

        for (ParameterStatement statement : statements) {
            dataRepo.update(statement);
        }
    }

    @DataSource(MultiDataSourceType.Master)
    public void delete(Long dataId, Long tableId) {
        DataTable table = tableRepo.find(tableId);

        ParameterStatement statement = statementBuilder.delete(table, dataId);

        dataRepo.update(statement);
    }

    @DataSource(MultiDataSourceType.Slaves)
    public List<Map<Long, Object>> query(QueryModel model) {
        Select select = parseSqlStatement(model.getStatement());

        return dataRepo.query(new ParameterStatement(select, model.getParameters()));
    }

    @DataSource(MultiDataSourceType.Slaves)
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

    private DataModel loadForTable(DataTable table, List<Long> includeColumns, DataColumn conditionColumn, Object conditionValue) {
        ParameterStatement statement = statementBuilder.select(table, includeColumns, conditionColumn, conditionValue);
        List<Map<Long, Object>> dataMaps = dataRepo.query(statement);

        DataModel dataModel = new DataModel();
        dataModel.setTableId(table.getId());
        dataModel.setDataMaps(dataMaps);
        return dataModel;
    }

    private List<ParameterStatement> saveForTable(DataModel model) {
        DataTable table = tableRepo.find(model.getTableId());
        List<Long> persistIds = fetchDatabase(table, model.getConditions());

        List<Map<Long, Object>> insertRows = new ArrayList<>();
        List<Map<Long, Object>> updateRows = new ArrayList<>();
        for (Map<Long, Object> dataMap : model.getDataMaps()) {
            Long id = (Long) dataMap.get(table.getPrimaryKey().getId());

            if (persistIds.contains(id)) {
                updateRows.add(dataMap);
                persistIds.remove(id);
            } else {
                insertRows.add(dataMap);
            }
        }

        List<ParameterStatement> statements = new ArrayList<>();
        if (insertRows.size() > 0)
            statements.add(statementBuilder.insert(table, model.getIncludeColumns(), insertRows));
        if (updateRows.size() > 0)
            statements.addAll(statementBuilder.update(table, model.getIncludeColumns(), updateRows));
        if (persistIds.size() > 0)
            statements.add(statementBuilder.delete(table, persistIds));
        return statements;
    }

    private List<Long> fetchDatabase(DataTable table, Map<Long, List<Object>> conditions) {
        ParameterStatement statement = statementBuilder.selectIn(table, conditions);

        return dataRepo.query(statement).stream()
                .map(m -> Long.valueOf(String.valueOf(m.get(table.getPrimaryKey().getId()))))
                .collect(Collectors.toList());
    }
}
