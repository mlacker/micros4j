package com.mlacker.micros.data.service;

import com.mlacker.micros.data.api.model.schema.TableModel;
import com.mlacker.micros.data.domain.schema.DataTable;
import com.mlacker.micros.data.domain.schema.TableRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

@Service
public class SchemaService {

    private final TableRepository repo;
    private final ModelMapper mapper;

    SchemaService(
            TableRepository repo,
            ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<TableModel> findAll() {
        List<DataTable> tables = repo.findAll();

        return mapper.map(tables, new TypeToken<List<TableModel>>() {
        }.getType());
    }

    public TableModel findSchema(Long id) {
        DataTable table = repo.find(id);

        return mapper.map(table, TableModel.class);
    }
}
