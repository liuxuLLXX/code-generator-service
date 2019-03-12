package net.vtstar.codegenerator.record.mapper;

import net.vtstar.codegenerator.record.domain.OperateRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * Date: 2019-3-12
 * @author liuxu
 */

@Mapper
public interface OperateRecordMapper {


    /**
     * 分页查询
     */
    List<OperateRecord> getList(OperateRecord record);

    /**
     * 根据id查询
     */
    OperateRecord getById(Long id);

    /**
     * 新增
     */
    void create(OperateRecord operateRecord);

    /**
     * 修改
     */
    void update(OperateRecord operateRecord);

    /**
     * 根据id逻辑删除
     */
    void deleteById(Long operateRecordId);
}
