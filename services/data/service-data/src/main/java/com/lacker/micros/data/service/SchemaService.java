package com.lacker.micros.data.service;

import com.lacker.micros.data.api.model.schema.TableModel;
import com.lacker.micros.data.domain.schema.DataTable;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.domain.exception.NotFoundAppException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

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
        DataTable table = repo.find(id)
                .orElseThrow(NotFoundAppException::new);

        return mapper.map(table, TableModel.class);
    }
}
