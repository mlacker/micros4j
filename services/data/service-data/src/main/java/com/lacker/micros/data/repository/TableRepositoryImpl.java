package com.lacker.micros.data.repository;

import com.lacker.micros.data.domain.schema.Table;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.data.repository.mapper.ColumnMapper;
import com.lacker.micros.data.repository.mapper.TableMapper;
import com.lacker.micros.domain.exception.NotFoundAppException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public List<Table> findAll() {
        return tableMapper.findAll();
    }

    @Override
    public Table findOne(String id) {
        return find(id).orElseThrow(NotFoundAppException::new);
    }

    @Override
    public Optional<Table> find(String id) {
        Table table = tableMapper.find(id);

        if (table != null) {
            table.setColumns(columnMapper.findByTable(id));
        }

        return Optional.ofNullable(table);
    }

    @Override
    public void save(Table table) {

    }
}
