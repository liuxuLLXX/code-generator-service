package net.vtstar.codegenerator.record.service;

import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.record.domain.CreateColumnRecord;
import net.vtstar.codegenerator.record.mapper.CreateColumnRecordMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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
public class CreateColumnRecordService{

    @Autowired
    private CreateColumnRecordMapper createColumnRecordMapper;

    /**
     * 分页查询
     */
    public List<CreateColumnRecord> pageList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CreateColumnRecord> createColumnRecordList = createColumnRecordMapper.getList();
        return createColumnRecordList;
    }

    /**
     *  新增
     */
    public void create(CreateColumnRecord createColumnRecord) {
        Assert.notNull(createColumnRecord, "createColumnRecord不能为null！");
        createColumnRecord.setStatus("1");
        createColumnRecord.setCreateTime(new Date());
        createColumnRecord.setUpdateTime(new Date());
        createColumnRecordMapper.create(createColumnRecord);

    }

    /**
     * 修改
     */
    public void update(CreateColumnRecord createColumnRecord) {
        Assert.notNull(createColumnRecord, "createColumnRecord不能为null！");
        Assert.notNull(createColumnRecord.getId(), "createColumnRecord不能为null！");
        createColumnRecordMapper.update(createColumnRecord);
    }


    /**
     * 根据id逻辑删除
     */
    public void deleteById(Long id) {
        createColumnRecordMapper.deleteById(id);
    }

}
