package net.vtstar.codegenerator.record.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.record.domain.OperateRecord;
import net.vtstar.codegenerator.record.service.OperateRecordService;
import net.vtstar.utils.asserts.ParamAssert;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import com.github.pagehelper.PageInfo;
import net.vtstar.utils.domain.Return;

/**
 * Date: 2019-3-12
 *
 * @author liuxu
 * @Description: 操作记录 Controller
 */

@Profile("pro")
@Slf4j
@Api(description = "操作记录模块")
@RestController
@RequestMapping("/api/operate")
public class OperateRecordController {

    @Autowired
    private OperateRecordService operateRecordService;

    @ApiOperation("分页查询列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "请求页码", dataType = "number", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页容量", dataType = "number", paramType = "query"),
            @ApiImplicitParam(name = "condition", value = "查询条件")
    })
    @GetMapping("/page")
    public Return pageList(@RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam(value = "condition", required = false) String condition) {
        ParamAssert.notNull(pageNum, "pageNum is null");
        ParamAssert.notNull(pageSize, "pageSize is null");
        List<OperateRecord> operateRecordList = operateRecordService.pageList(pageNum, pageSize, condition);
        return Return.success(new PageInfo<>(operateRecordList));
    }

}
