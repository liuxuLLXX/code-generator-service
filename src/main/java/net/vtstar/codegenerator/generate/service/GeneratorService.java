package net.vtstar.codegenerator.generate.service;

import net.vtstar.codegenerator.generate.domain.GenVo;

/**
 * @Auther: liuxu
 * @Date: 2019/3/13
 * @Description:
 */
public interface GeneratorService {

    void doGenerator(GenVo genVo) throws Exception;
}
