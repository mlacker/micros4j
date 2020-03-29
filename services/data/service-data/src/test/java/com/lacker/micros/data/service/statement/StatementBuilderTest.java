package com.lacker.micros.data.service.statement;

import com.lacker.micros.data.domain.schema.DataTable;
import com.lacker.micros.data.domain.statement.ParameterStatement;
import com.lacker.micros.data.service.TableBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatementBuilderTest {

    private StatementBuilder builder;
    private DataTable table;

    @Before
    public void setUp() {
        this.builder = new StatementBuilder();

        this.table = TableBuilder.createTable("ti-a", "tn-a")
                .createIdColumn("ci-ai")
                .createColumn("ci-f1", "cn-f1")
                .createColumn("ci-a1", "cn-a1")
                .createColumn("ci-a2", "cn-a2")
                .createColumn("ci-a3", "cn-a3")
                .createColumn("ci-ad", "deleted")
                .build();
    }

    @Test
    public void selectById() {
        ParameterStatement statement =
                builder.select(this.table, includeColumns(), this.table.getPrimaryKey(), "1");

        Assert.assertEquals(
                "SELECT `id` AS `ci-ai`, `cn-a1` AS `ci-a1`, `cn-a2` AS `ci-a2` FROM `tn-a` WHERE (`id` = ?) AND `deleted` = 0",
                statement.toString());
    }

    @Test
    public void selectByForeignKey() {
        ParameterStatement statement =
                builder.select(this.table, includeColumns(), this.table.getColumn("ci-f1"), "1");

        Assert.assertEquals(
                "SELECT `id` AS `ci-ai`, `cn-a1` AS `ci-a1`, `cn-a2` AS `ci-a2` FROM `tn-a` WHERE (`cn-f1` = ?) AND `deleted` = 0",
                statement.toString());
    }

    @Test
    public void selectIn() {
        Map<String, List<Object>> conditions = new LinkedHashMap<>();
        conditions.put("ci-ai", Arrays.asList("1", "2", "3"));
        conditions.put("ci-f1", Arrays.asList("1", "2"));

        ParameterStatement statement =
                builder.selectIn(this.table, conditions);

        Assert.assertEquals(
                "SELECT `id` AS `ci-ai` FROM `tn-a` WHERE (`id` IN (?, ?, ?) OR `cn-f1` IN (?, ?)) AND `deleted` = 0",
                statement.toString());
    }

    @Test
    public void insert() {
        ParameterStatement statement =
                builder.insert(this.table, includeColumns(), dataMaps());

        Assert.assertEquals(
                "INSERT INTO `tn-a` (`id`, `cn-a1`, `cn-a2`) VALUES (?, ?, ?), (?, ?, ?)",
                statement.toString());
    }

    @Test
    public void update() {
        List<ParameterStatement> statements =
                builder.update(this.table, includeColumns(), dataMaps());

        Assert.assertEquals(
                "UPDATE `tn-a` SET `cn-a1` = ?, `cn-a2` = ? WHERE `id` = ?",
                statements.get(0).toString());
        Assert.assertEquals(
                "UPDATE `tn-a` SET `cn-a1` = ?, `cn-a2` = ? WHERE `id` = ?",
                statements.get(1).toString());

        Assert.assertArrayEquals(new Object[]{"2", "3", "1"}, statements.get(0).getParameters());
    }

    @Test
    public void deleteByIds() {
        ParameterStatement statement =
                builder.delete(this.table, Arrays.asList("1", "2", "3"));

        Assert.assertEquals(
                "UPDATE `tn-a` SET `deleted` = 1 WHERE `id` IN (?, ?, ?)",
                statement.toString());
    }

    @Test
    public void delete() {
        ParameterStatement statement =
                builder.delete(this.table, "1");

        Assert.assertEquals(
                "UPDATE `tn-a` SET `deleted` = 1 WHERE `id` IN (?)",
                statement.toString());
    }

    private List<String> includeColumns() {
        return Arrays.asList("ci-ai", "ci-a1", "ci-a2");
    }

    private List<Map<String, Object>> dataMaps() {
        List<Map<String, Object>> dataMaps = new ArrayList<>();
        Map<String, Object> dataMap1 = new HashMap<>();
        dataMap1.put("ci-ai", "1");
        dataMap1.put("ci-a1", "2");
        dataMap1.put("ci-a2", "3");
        dataMaps.add(dataMap1);
        Map<String, Object> dataMap2 = new HashMap<>();
        dataMap2.put("ci-ai", "4");
        dataMap2.put("ci-a1", "5");
        dataMap2.put("ci-a2", "6");
        dataMaps.add(dataMap2);
        return dataMaps;
    }
}