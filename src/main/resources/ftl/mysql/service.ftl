package ${pkgName}.${meta.module}.${serviceFolder};

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import net.vtstar.utils.asserts.ParamAssert;
import java.util.List;
import com.github.pagehelper.PageHelper;
import java.util.Date;
import ${pkgName}.${meta.module}.${mapperFolder}.${meta.className}${MapperSuffix};
import ${pkgName}.${meta.module}.${domainFolder}.${meta.className};

/**
 * Date: ${currentDate}
 * @author ${author}
 * @Description:
 */
@Slf4j
@Service
public class ${meta.className}${ServiceSuffix} {

    @Autowired
    private ${meta.className}${MapperSuffix} ${meta.firstLowerClassName}${MapperSuffix};

    /**
     *  分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return ${meta.firstLowerClassName}List
     */
    public List<${meta.className}> pageList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<${meta.className}> ${meta.firstLowerClassName}List = ${meta.firstLowerClassName}${MapperSuffix}.getList();
        return ${meta.firstLowerClassName}List;
    }

    /**
     * 新增
     *
     * @param ${meta.firstLowerClassName}
     */
    public void create(${meta.className} ${meta.firstLowerClassName}) {
        ParamAssert.notNull(${meta.firstLowerClassName}, "${meta.firstLowerClassName}不能为null！");
        ${meta.firstLowerClassName}.setStatus("1");
        ${meta.firstLowerClassName}.setCreateTime(new Date());
        ${meta.firstLowerClassName}.setUpdateTime(new Date());
        ${meta.firstLowerClassName}${MapperSuffix}.create(${meta.firstLowerClassName});

    }

    /**
     * 修改
     *
     * @param ${meta.firstLowerClassName}
     */
    public void update(${meta.className} ${meta.firstLowerClassName}) {
        ParamAssert.notNull(${meta.firstLowerClassName}, "${meta.firstLowerClassName}不能为null！");
        ParamAssert.notNull(${meta.firstLowerClassName}.getId(), "id不能为null！");
        ${meta.firstLowerClassName}.setUpdateTime(new Date());
        ${meta.firstLowerClassName}${MapperSuffix}.update(${meta.firstLowerClassName});
    }

    /**
     * 根据id逻辑删除
     *
     * @param id
     */
    public void deleteById(Long id) {
        ${meta.firstLowerClassName}${MapperSuffix}.deleteById(id);
    }

}
