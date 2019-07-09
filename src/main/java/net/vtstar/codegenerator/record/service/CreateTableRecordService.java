package net.vtstar.codegenerator.record.service;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.record.domain.CreateTableRecord;
import net.vtstar.codegenerator.record.mapper.CreateTableRecordMapper;
import net.vtstar.utils.asserts.ParamAssert;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.github.pagehelper.PageHelper;
import java.util.Date;

/**
 * Date: 2019-3-12
 * @author liuxu
 * @Description:
 */
@Profile("pro")
@Slf4j
@Service
public class CreateTableRecordService{

    @Autowired
    private CreateTableRecordMapper createTableRecordMapper;

    /**
     * 分页查询
     */
    public PageInfo<CreateTableRecord> pageList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CreateTableRecord> createTableRecordList = createTableRecordMapper.getList();
        return new PageInfo<>(createTableRecordList);
    }

    /**
     *  新增
     */
    public void create(CreateTableRecord createTableRecord) {
        ParamAssert.notNull(createTableRecord, "createTableRecord不能为null！");
        createTableRecord.setStatus("1");
        createTableRecord.setCreateTime(new Date());
        createTableRecord.setUpdateTime(new Date());
        createTableRecordMapper.create(createTableRecord);

    }

    /**
     * 修改
     */
    public void update(CreateTableRecord createTableRecord) {
        ParamAssert.notNull(createTableRecord, "createTableRecord不能为null！");
        ParamAssert.notNull(createTableRecord.getId(), "createTableRecord不能为null！");
        createTableRecordMapper.update(createTableRecord);
    }

    /**
     * 根据id逻辑删除
     */
    public void deleteById(Long id) {
        createTableRecordMapper.deleteById(id);
    }

}
