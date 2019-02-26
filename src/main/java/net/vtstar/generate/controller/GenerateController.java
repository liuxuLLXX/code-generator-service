package net.vtstar.generate.controller;

import net.vtstar.generate.domain.GenVo;
import net.vtstar.generate.domain.GeneratorConfig;
import net.vtstar.generate.domain.Table;
import net.vtstar.generate.service.GeneratorService;
import net.vtstar.generate.service.MetaService;
import net.vtstar.utils.domain.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Auther: liuxu
 * @Date: 2019/2/21
 * @Description:
 */
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
        Set<Table> tables = metaService.getTables(genVo.getConfig()).getTables();
        generatorService.doGenerator(genVo.getConfig(), genVo.getTables());
        return Return.success();
    }
}
