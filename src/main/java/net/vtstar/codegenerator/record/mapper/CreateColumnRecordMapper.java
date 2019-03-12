package net.vtstar.codegenerator.record.mapper;

import net.vtstar.codegenerator.record.domain.CreateColumnRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date: 2019-3-12
 * @author liuxu
 */

@Mapper
public interface CreateColumnRecordMapper {

    /**
     * 分页查询
     */
    List<CreateColumnRecord> getList();


    /**
     * 根据id查询
     */
    CreateColumnRecord getById(Long id);

    /**
     * 新增
     */
    void create(CreateColumnRecord createColumnRecord);

    /**
     * 修改
     */
    void update(CreateColumnRecord createColumnRecord);

    /**
     * 根据id逻辑删除
     */
    void deleteById(Long createColumnRecordId);


    List<CreateColumnRecord> getByTableId(@Param("tableId") Long tableId);
}
