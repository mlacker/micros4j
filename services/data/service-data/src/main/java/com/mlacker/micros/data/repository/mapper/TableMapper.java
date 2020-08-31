package com.mlacker.micros.data.repository.mapper;

import com.mlacker.micros.data.domain.schema.DataTable;
import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface TableMapper {

    @Select("SELECT * FROM data_table")
    List<DataTable> findAll();

    DataTable find(Long id);
}
