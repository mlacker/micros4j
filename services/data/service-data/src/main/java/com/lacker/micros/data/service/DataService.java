package com.lacker.micros.data.service;

import com.lacker.micros.data.api.model.data.DataModel;
import com.lacker.micros.data.api.model.data.QueryModel;
import com.lacker.micros.data.domain.data.DataRepository;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.domain.exception.InvalidParameterAppException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    private final DataRepository dataRepo;
    private final TableRepository tableRepo;

    public DataService(DataRepository dataRepo, TableRepository tableRepo) {
        this.dataRepo = dataRepo;
        this.tableRepo = tableRepo;
    }

    public DataModel query(QueryModel model) {
        /*
         * 1. table transform
         * 2. column transform
         * 3. params transform
         */
        Select select;

        try {
            Statement statement = CCJSqlParserUtil.parse(model.getStatement());

            if (!(statement instanceof Select)) {
                throw new UnsupportedOperationException("Only support select statement");
            }

            select = (Select) statement;
        } catch (JSQLParserException ex) {
            throw new InvalidParameterAppException("Invalid sql statement", ex);
        }

        // ParamsTransformer;
        SchemaTransformer schemaTransformer = new SchemaTransformer(tableRepo);
        select.accept(schemaTransformer);
        return null;
    }
}
