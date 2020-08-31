package com.mlacker.micros.data.domain.schema;

import com.mlacker.micros.domain.repository.Repository;
import java.util.List;

public interface TableRepository extends Repository<DataTable> {

    List<DataTable> findAll();
}
