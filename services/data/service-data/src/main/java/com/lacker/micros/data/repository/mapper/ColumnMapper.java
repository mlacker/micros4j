package com.lacker.micros.data.repository.mapper;

import com.lacker.micros.data.domain.schema.Column;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ColumnMapper {

    @Select("SELECT * FROM data_column WHERE table_id = #{tableId}")
    List<Column> findByTable(String tableId);
}
