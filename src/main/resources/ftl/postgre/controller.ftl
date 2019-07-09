package ${pkgName}.${meta.module}.${controllerFolder};

import ${pkgName}.${meta.module}.${domainFolder}.${meta.className};
import ${pkgName}.${meta.module}.${serviceFolder}.${meta.className}${ServiceSuffix};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import net.vtstar.utils.asserts.ParamAssert;
import java.util.List;
import javax.validation.Valid;
import com.github.pagehelper.PageInfo;
import net.vtstar.utils.domain.Return;

/**
 * Date: ${currentDate}
 *
 * @author ${author}
 * @Description: ${meta.tableDesc} Controller
 */

@Slf4j
@Api(description = "${meta.tableDesc}")
@RestController
@RequestMapping("/api/${meta.firstLowerClassName}")
public class ${meta.className}${ControllerSuffix} {

    @Autowired
    private ${meta.className}${ServiceSuffix} ${meta.firstLowerClassName}${ServiceSuffix};

    @ApiOperation("分页查询列表")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "pageNum", value = "请求页码", dataType = "number", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "页容量", dataType = "number", paramType = "query"),
    })
    @GetMapping("/page")
    private Return pageList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        ParamAssert.notNull(pageNum, "pageNum is null");
        ParamAssert.notNull(pageSize, "pageSize is null");
        List<${meta.className}> ${meta.firstLowerClassName}List = ${meta.firstLowerClassName}${ServiceSuffix}.pageList(pageNum, pageSize);
        return Return.success(new PageInfo<>(${meta.firstLowerClassName}List));
    }

    @ApiOperation("新建${meta.tableDesc}")
    @PostMapping
    private Return save(@Valid @RequestBody ${meta.className} ${meta.firstLowerClassName}) {
        ${meta.firstLowerClassName}${ServiceSuffix}.save(${meta.firstLowerClassName});
        return Return.success();
    }

    @ApiOperation("修改${meta.tableDesc}")
    @PutMapping
    private Return update(@Valid @RequestBody ${meta.className} ${meta.firstLowerClassName}) {
        ParamAssert.notNull(${meta.firstLowerClassName}.getId(), "id is null");
        ${meta.firstLowerClassName}${ServiceSuffix}.update(${meta.firstLowerClassName});
        return Return.success();
    }

    @ApiOperation("删除${meta.tableDesc}（根据id删除单个${meta.tableDesc}）")
    @DeleteMapping("/{id}")
    private Return delete(@PathVariable <#list meta.pkCols as pkCol><#if pkCol_index == 0>${pkCol.javaType}</#if></#list> id) {
        ParamAssert.notNull(id, "${meta.tableDesc}Id is null");
        ${meta.firstLowerClassName}Service.deleteById(id);
        return Return.success();
    }

}
