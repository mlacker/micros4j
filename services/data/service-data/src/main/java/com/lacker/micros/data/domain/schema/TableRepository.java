package com.lacker.micros.data.domain.schema;

import java.util.List;

public interface TableRepository {

    List<Table> findAll();

    Table find(String id);
}
