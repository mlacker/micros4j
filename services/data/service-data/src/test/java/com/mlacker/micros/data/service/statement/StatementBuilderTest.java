package com.mlacker.micros.data.service.statement;

import com.mlacker.micros.data.domain.schema.DataTable;
import com.mlacker.micros.data.domain.statement.ParameterStatement;
import com.mlacker.micros.data.service.TableBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StatementBuilderTest {

    private StatementBuilder builder;
    private DataTable table;

    @Before
    public void setUp() {
        this.builder = new StatementBuilder();

        this.table = TableBuilder.createTable(100L, "tn-a")
                .createIdColumn(1101L)
                .createColumn(1102L, "cn-f1")
                .createColumn(1111L, "cn-a1")
                .createColumn(1112L, "cn-a2")
                .createColumn(1113L, "cn-a3")
                .createColumn(1103L, "deleted")
                .build();
    }

    @Test
    public void selectById() {
        ParameterStatement statement =
                builder.select(this.table, includeColumns(), this.table.getPrimaryKey(), 1L);

        Assert.assertEquals(
                "SELECT `id` AS `1101`, `cn-a1` AS `1111`, `cn-a2` AS `1112` FROM `tn-a` WHERE (`id` = ?) AND `deleted` = 0",
                statement.toString());
    }

    @Test
    public void selectByForeignKey() {
        ParameterStatement statement =
                builder.select(this.table, includeColumns(), this.table.getColumn(1102L), 1L);

        Assert.assertEquals(
                "SELECT `id` AS `1101`, `cn-a1` AS `1111`, `cn-a2` AS `1112` FROM `tn-a` WHERE (`cn-f1` = ?) AND `deleted` = 0",
                statement.toString());
    }

    @Test
    public void selectIn() {
        Map<Long, List<Object>> conditions = new LinkedHashMap<>();
        conditions.put(1101L, Arrays.asList(1L, 2L, 3L));
        conditions.put(1102L, Arrays.asList(1L, 2L));

        ParameterStatement statement =
                builder.selectIn(this.table, conditions);

        Assert.assertEquals(
                "SELECT `id` AS `1101` FROM `tn-a` WHERE (`id` IN (?, ?, ?) OR `cn-f1` IN (?, ?)) AND `deleted` = 0",
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

        Assert.assertArrayEquals(new Object[]{2L, 3L, 1L}, statements.get(0).getParameters());
    }

    @Test
    public void deleteByIds() {
        ParameterStatement statement =
                builder.delete(this.table, Arrays.asList(1L, 2L, 3L));

        Assert.assertEquals(
                "UPDATE `tn-a` SET `deleted` = 1 WHERE `id` IN (?, ?, ?)",
                statement.toString());
    }

    @Test
    public void delete() {
        ParameterStatement statement =
                builder.delete(this.table, 1L);

        Assert.assertEquals(
                "UPDATE `tn-a` SET `deleted` = 1 WHERE `id` IN (?)",
                statement.toString());
    }

    private List<Long> includeColumns() {
        return Arrays.asList(1101L, 1111L, 1112L);
    }

    private List<Map<Long, Object>> dataMaps() {
        List<Map<Long, Object>> dataMaps = new ArrayList<>();
        Map<Long, Object> dataMap1 = new HashMap<>();
        dataMap1.put(1101L, 1L);
        dataMap1.put(1111L, 2L);
        dataMap1.put(1112L, 3L);
        dataMaps.add(dataMap1);
        Map<Long, Object> dataMap2 = new HashMap<>();
        dataMap2.put(1101L, 4L);
        dataMap2.put(1111L, 5L);
        dataMap2.put(1112L, 6L);
        dataMaps.add(dataMap2);
        return dataMaps;
    }
}