package com.lacker.micros.data.service;

import com.lacker.micros.data.api.model.data.QueryModel;
import com.lacker.micros.data.domain.data.DataRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DataServiceTest {

    private DataService service;
    private DataRepository dataRepo;

    @Before
    public void setUp() {
        this.dataRepo = mock(DataRepository.class);
        SchemaTransformer schemaTransformer = mock(SchemaTransformer.class);

        this.service = new DataService(dataRepo, null, schemaTransformer);
    }

    @Test
    public void queryLimitLessThen1000Test() {
        QueryModel model = new QueryModel();

        model.setStatement("SELECT * FROM table");
        try {
            service.query(model);
            fail("Expected for the UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // ignored
        }

        model.setStatement("SELECT * FROM table LIMIT 1000");
        service.query(model);
        verify(dataRepo).query(any(), any());

        model.setStatement("SELECT * FROM table LIMIT 1001");
        try {
            service.query(model);
            fail("Expected for the UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // ignored
        }
    }

    @Ignore
    public void queryTest() {
        QueryModel model = new QueryModel();
        model.setStatement(
                "SELECT * FROM `1695CC54-2020-0074-B175-4B08C61C9F5E` AS `1695CC54-2020-0074-B175-4B08C61C9F5E` " +
                        "WHERE `1695CC54-2020-0074-B175-4B08C61C9F5E`.`1695CC54-2020-0073-B176-932268F0C22E` = :id");
        model.setParams(new HashMap<>());
        model.getParams().put("id", "1695CC8F-3E30-001F-8497-4FAE33CCF3FE");

        service.query(model);
    }
}
