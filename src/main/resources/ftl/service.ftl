package ${pkgName}.${serviceFolder};

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import java.util.List;
import com.github.pagehelper.PageHelper;
import ${pkgName}.${meta.module}.${mapperFolder}.${meta.className}${MapperSuffix};
import ${pkgName}.${meta.module}.${domainFolder}.${meta.className};

/**
 * Date: ${currentDate}
 * @author ${author}
 * @Description:
 */
@Slf4j
@Service
public class ${meta.className}${ServiceSuffix}{

    @Autowired
    private ${meta.className}${MapperSuffix}  ${meta.firstLowerClassName}${MapperSuffix};

    /**
     * 分页查询
     */
    public List<${meta.className}> pageList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<${meta.className}> ${meta.firstLowerClassName}List = ${meta.firstLowerClassName}${MapperSuffix}.getList();
        return ${meta.firstLowerClassName}List;
    }

    /**
     *  新增
     */
    public void create(${meta.className} ${meta.firstLowerClassName}) {
        Assert.notNull(${meta.firstLowerClassName}, "${meta.firstLowerClassName}不能为null！");
            if (null == ${meta.firstLowerClassName}.getId()) {
                ${meta.firstLowerClassName}${MapperSuffix}.create(${meta.firstLowerClassName});
            } else {
                ${meta.firstLowerClassName}${MapperSuffix}.update(${meta.firstLowerClassName});
            }
    }

    /**
     * 修改
     */
    public void update(${meta.className} ${meta.firstLowerClassName}) {
        Assert.notNull(${meta.firstLowerClassName}, "${meta.firstLowerClassName}不能为null！");
        Assert.notNull(${meta.firstLowerClassName}.getId(), "${meta.firstLowerClassName}不能为null！");
        ${meta.firstLowerClassName}${MapperSuffix}.update(${meta.firstLowerClassName});
    }


    /**
     * 根据id逻辑删除
     */
    public void deleteById(Long id) {
        ${meta.firstLowerClassName}${MapperSuffix}.deleteById(id);
    }

}
