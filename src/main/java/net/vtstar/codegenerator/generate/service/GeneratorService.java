package net.vtstar.codegenerator.generate.service;

import net.vtstar.codegenerator.generate.domain.GeneratorConfig;
import net.vtstar.codegenerator.generate.domain.Table;

import java.util.List;

/**
 * @Auther: liuxu
 * @Date: 2019/3/13
 * @Description:
 */
public interface GeneratorService {

    void doGenerator(GeneratorConfig conf, List<Table> choseTables) throws Exception;
}
