package net.vtstar.codegenerator.record.mapper;

import net.vtstar.codegenerator.record.domain.CreateTableRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date: 2019-3-12
 * @author liuxu
 */

@Mapper
public interface CreateTableRecordMapper {

    /**
     * 分页查询
     */
    List<CreateTableRecord> getList();

    /**
     * 根据id查询
     */
    CreateTableRecord getById(Long id);

    /**
     * 新增
     */
    void create(CreateTableRecord createTableRecord);

    /**
     * 修改
     */
    void update(CreateTableRecord createTableRecord);

    /**
     * 根据id逻辑删除
     */
    void deleteById(Long createTableRecordId);


    List<CreateTableRecord> getByRecordId(@Param("recordId") Long recordId);
}
