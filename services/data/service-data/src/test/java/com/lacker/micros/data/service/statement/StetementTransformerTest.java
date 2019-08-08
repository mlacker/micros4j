package com.lacker.micros.data.service.statement;

import com.lacker.micros.data.domain.schema.DataTable;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.data.service.TableBuilder;
import com.lacker.micros.domain.exception.InvalidOperationAppException;
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
        DataTable tableA = TableBuilder.createTable("ti-a", "tn-a")
                .createColumn("ci-a1", "cn-a1")
                .build();
        DataTable tableB = TableBuilder.createTable("ti-b", "tn-b")
                .createColumn("ci-b1", "cn-b1")
                .build();

        TableRepository repo = Mockito.mock(TableRepository.class);
        doReturn(Optional.of(tableA)).when(repo).find(eq("ti-a"));
        doReturn(Optional.of(tableB)).when(repo).find(eq("ti-b"));

        this.transformer = new StetementTransformer(repo);
    }

    @Test
    public void tableTest() {
        assertStatement(
                "SELECT * FROM `tn-a`",
                "SELECT * FROM `ti-a`");
    }

    @Test
    public void columnTest() {
        assertStatement(
                "SELECT `ti-a`.`cn-a1` FROM `tn-a` AS `ti-a`",
                "SELECT `ti-a`.`ci-a1` FROM `ti-a` AS `ti-a`");
    }

    @Test
    public void joinTest() {
        assertStatement(
                "SELECT `ti-a`.`cn-a1`, `ti-b`.`cn-b1` " +
                        "FROM `tn-a` AS `ti-a` " +
                        "JOIN `tn-b` AS `ti-b` ON `ti-a`.`ci-a1` = `ti-b`.`ci-b1`",
                "SELECT `ti-a`.`ci-a1`, `ti-b`.`ci-b1`" +
                        "FROM `ti-a` AS `ti-a` " +
                        "JOIN `ti-b` AS `ti-b` ON `ti-a`.`ci-a1` = `ti-b`.`ci-b1`"
        );
    }

    @Test(expected = InvalidOperationAppException.class)
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
                "SELECT `ci-a1` FROM `ti-a`"
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