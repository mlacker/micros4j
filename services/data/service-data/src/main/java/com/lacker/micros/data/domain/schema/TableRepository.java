package com.lacker.micros.data.domain.schema;

import com.lacker.micros.domain.repository.Repository;

import java.util.List;

public interface TableRepository extends Repository<DataTable> {

    DataTable findOne(String id);

    List<DataTable> findAll();
}
