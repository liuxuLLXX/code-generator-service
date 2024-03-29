package ${pkgName}.${meta.module}.${mapperFolder};

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import ${pkgName}.${meta.module}.${domainFolder}.${meta.className};

/**
 * Date: ${currentDate}
 * @author ${author}
 */

@Mapper
public interface ${meta.className}${MapperSuffix} {

    /**
     * 分页查询
     *
     * @return List<${meta.className}>
     */
    List<${meta.className}> findList();

    /**
     * 根据id查询
     *
     * @param id
     * @return ${meta.className}
     */
    ${meta.className} getById(<#list meta.pkCols as pkCol><#if pkCol_index == 0>${pkCol.javaType}</#if></#list> id);

    /**
     * 新增
     *
     * @param ${meta.firstLowerClassName}
     */
    void save(${meta.className} ${meta.firstLowerClassName});

    /**
     * 修改
     *
     * @param ${meta.firstLowerClassName}
     */
    void update(${meta.className} ${meta.firstLowerClassName});

    /**
     * 根据id逻辑删除
     *
     * @param ${meta.firstLowerClassName}Id
     */
    void deleteById(<#list meta.pkCols as pkCol><#if pkCol_index == 0>${pkCol.javaType}</#if></#list> ${meta.firstLowerClassName}Id);
}
