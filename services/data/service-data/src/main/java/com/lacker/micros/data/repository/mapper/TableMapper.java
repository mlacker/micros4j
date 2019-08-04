package com.lacker.micros.data.repository.mapper;

import com.lacker.micros.data.domain.schema.DataTable;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TableMapper {

    @Select("SELECT * FROM data_table")
    List<DataTable> findAll();

    DataTable find(String id);
}
