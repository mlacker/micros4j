package com.mlacker.micros.data.repository.mapper;

import com.mlacker.micros.data.domain.schema.DataColumn;
import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface ColumnMapper {

    @Select("SELECT * FROM data_column WHERE table_id = #{tableId}")
    List<DataColumn> findByTable(Long tableId);
}
