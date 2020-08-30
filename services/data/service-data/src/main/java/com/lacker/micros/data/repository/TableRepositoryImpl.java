package com.lacker.micros.data.repository;

import com.lacker.micros.data.domain.schema.DataTable;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.data.repository.mapper.ColumnMapper;
import com.lacker.micros.data.repository.mapper.TableMapper;
import com.mlacker.micros.domain.exception.NotFoundAppException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TableRepositoryImpl implements TableRepository {

    private final TableMapper tableMapper;
    private final ColumnMapper columnMapper;

    TableRepositoryImpl(
            TableMapper tableMapper,
            ColumnMapper columnMapper) {
        this.tableMapper = tableMapper;
        this.columnMapper = columnMapper;
    }

    @Override
    public List<DataTable> findAll() {
        return tableMapper.findAll();
    }

    @NotNull
    @Override
    public DataTable find(long id) {
        DataTable table = tableMapper.find(id);

        if (table == null) {
            throw new NotFoundAppException();
        }

        table.setColumns(columnMapper.findByTable(id));

        return table;
    }

    @Override
    public void save(@NotNull DataTable table) {

    }
}
