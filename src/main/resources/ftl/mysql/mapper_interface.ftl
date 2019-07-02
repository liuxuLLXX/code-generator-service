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
    List<${meta.className}> getList();


    /**
     * 根据id查询
     *
     * @param id
     * @return ${meta.className}
     */
    ${meta.className} getById(Long id);

    /**
     * 新增
     *
     * @param ${meta.firstLowerClassName}
     */
    void create(${meta.className} ${meta.firstLowerClassName});

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
    void deleteById(Long ${meta.firstLowerClassName}Id);
}
