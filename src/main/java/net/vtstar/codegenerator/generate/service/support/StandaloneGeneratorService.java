package net.vtstar.codegenerator.generate.service.support;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.generate.domain.GenVo;
import net.vtstar.codegenerator.generate.service.DefaultGenerator;
import net.vtstar.codegenerator.generate.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Auther: liuxu
 * @Date: 2019/3/13
 * @Description:
 */
@Profile("!pro")
@Slf4j
@Service
public class StandaloneGeneratorService implements GeneratorService {

    @Autowired
    DefaultGenerator defaultGenerator;
    /**
     * 根据表名生成文件。
     *
     * @param genVo        代码生成配置
     * @throws IOException       抛出的IO异常
     * @throws TemplateException 抛出的freemarker异常
     */
    @Override
    public void doGenerator(GenVo genVo) throws Exception{
        defaultGenerator.doGenerator(genVo);
    }
}
