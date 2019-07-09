package net.vtstar.codegenerator.generate.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.generate.domain.DataSourceParams;
import net.vtstar.codegenerator.generate.domain.GenVo;
import net.vtstar.codegenerator.generate.domain.Table;
import net.vtstar.codegenerator.generate.service.GeneratorService;
import net.vtstar.codegenerator.generate.service.MetaService;
import net.vtstar.utils.domain.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Auther: liuxu
 * @Date: 2019/2/21
 * @Description:
 */
@Profile("standalone")
@Slf4j
@Api(description = "代码生成模块")
@RestController
@RequestMapping("/api/generator")
public class GenerateStandaloneController {

    @Autowired
    private MetaService metaService;
    @Autowired
    private GeneratorService generatorService;

    @PostMapping("/allTables")
    private Return allTables(@RequestBody DataSourceParams params) throws Exception {
        Set<Table> tables = metaService.getTables(params).getTables();
        return Return.success(tables);
    }

    @PostMapping("/createCodeStandalone")
    public Return createCodeStandalone(@RequestBody GenVo genVo) throws Exception {
        generatorService.doGenerator(genVo);
        return Return.success();
    }

}
