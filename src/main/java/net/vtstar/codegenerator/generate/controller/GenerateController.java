package net.vtstar.codegenerator.generate.controller;

import io.swagger.annotations.Api;
import net.vtstar.codegenerator.generate.domain.GenVo;
import net.vtstar.codegenerator.generate.domain.GeneratorConfig;
import net.vtstar.codegenerator.generate.domain.Table;
import net.vtstar.codegenerator.generate.service.GeneratorService;
import net.vtstar.codegenerator.generate.service.MetaService;
import net.vtstar.utils.domain.Return;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api
@RestController
@RequestMapping("/api/generator")
public class GenerateController {

    @Autowired
    private MetaService metaService;
    @Autowired
    private GeneratorService generatorService;

    @PostMapping("/allTables")
    private Return allTables(@RequestBody GeneratorConfig config) throws Exception {
        Set<Table> tables = metaService.getTables(config).getTables();
        return Return.success(tables);
    }

    @PostMapping("/createCode")
    public Return createCode(@RequestBody GenVo genVo) throws Exception {
        generatorService.doGenerator(genVo.getConfig(), genVo.getTables());
        return Return.success();
    }
}
