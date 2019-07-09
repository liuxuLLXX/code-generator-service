package net.vtstar.codegenerator.record.service;

import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.record.domain.OperateRecord;
import net.vtstar.codegenerator.record.mapper.OperateRecordMapper;
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
public class OperateRecordService{

    @Autowired
    private OperateRecordMapper operateRecordMapper;

    /**
     * 分页查询
     */
    public List<OperateRecord> pageList(Integer pageNum, Integer pageSize, String condition) {
        PageHelper.startPage(pageNum, pageSize);
        OperateRecord record = new OperateRecord();
        record.setName(condition);
        List<OperateRecord> operateRecordList = operateRecordMapper.getList(record);
        return operateRecordList;
    }

    /**
     *  新增
     */
    public void create(OperateRecord operateRecord) {
        ParamAssert.notNull(operateRecord, "operateRecord不能为null！");
        operateRecord.setStatus("1");
        operateRecord.setCreateTime(new Date());
        operateRecord.setUpdateTime(new Date());
        operateRecordMapper.create(operateRecord);

    }

    /**
     * 修改
     */
    public void update(OperateRecord operateRecord) {
        ParamAssert.notNull(operateRecord, "operateRecord不能为null！");
        ParamAssert.notNull(operateRecord.getId(), "operateRecord不能为null！");
        operateRecordMapper.update(operateRecord);
    }

    /**
     * 根据id逻辑删除
     */
    public void deleteById(Long id) {
        operateRecordMapper.deleteById(id);
    }

}
