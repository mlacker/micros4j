package com.lacker.micros.data.service.statement;

import com.lacker.micros.data.domain.schema.DataTable;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.data.service.TableBuilder;
import com.lacker.micros.domain.exception.InvalidOperationAppException;
import com.lacker.micros.domain.exception.InvalidParameterAppException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

public class StetementTransformerTest {

    private StetementTransformer transformer;

    @Before
    public void setUp() {
        DataTable tableA = TableBuilder.createTable(100L, "tn-a")
                .createColumn(1111L, "cn-a1")
                .build();
        DataTable tableB = TableBuilder.createTable(200L, "tn-b")
                .createColumn(1211L, "cn-b1")
                .build();

        TableRepository repo = Mockito.mock(TableRepository.class);
        doReturn(Optional.of(tableA)).when(repo).find(eq(100L));
        doReturn(Optional.of(tableB)).when(repo).find(eq(200L));

        this.transformer = new StetementTransformer(repo);
    }

    @Test
    public void tableTest() {
        assertStatement(
                "SELECT * FROM `tn-a`",
                "SELECT * FROM `100`");
    }

    @Test
    public void columnTest() {
        assertStatement(
                "SELECT `100`.`cn-a1` FROM `tn-a` AS `100`",
                "SELECT `100`.`1111` FROM `100` AS `100`");
    }

    @Test
    public void joinTest() {
        assertStatement(
                "SELECT `100`.`cn-a1`, `200`.`cn-b1` " +
                        "FROM `tn-a` AS `100` " +
                        "JOIN `tn-b` AS `200` ON `100`.`1111` = `200`.`1211`",
                "SELECT `100`.`1111`, `200`.`1211`" +
                        "FROM `100` AS `100` " +
                        "JOIN `200` AS `200` ON `100`.`1111` = `200`.`1211`"
        );
    }

    @Test(expected = InvalidParameterAppException.class)
    public void illegalTableTest() {
        assertStatement(
                "",
                "SELECT * FROM `unknown`"
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void columnWithoutTableTest() {
        assertStatement(
                "",
                "SELECT `1111` FROM `100`"
        );
    }

    private void assertStatement(String expected, String query) {
        try {
            Statement statement = CCJSqlParserUtil.parse(query);
            statement.accept(transformer);

            assertEquals(expected, statement.toString());
        } catch (JSQLParserException ex) {
            fail(ex.getCause().getMessage());
        }
    }
}