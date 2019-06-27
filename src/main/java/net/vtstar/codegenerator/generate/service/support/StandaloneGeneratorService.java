package net.vtstar.codegenerator.generate.service.support;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.generate.advice.exception.GeneratorException;
import net.vtstar.codegenerator.generate.domain.FreemarkerTemplate;
import net.vtstar.codegenerator.generate.domain.GenVo;
import net.vtstar.codegenerator.generate.domain.GeneratorConfig;
import net.vtstar.codegenerator.generate.domain.Table;
import net.vtstar.codegenerator.generate.service.DefaultGenerator;
import net.vtstar.codegenerator.generate.service.GeneratorService;
import net.vtstar.codegenerator.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.DateFormat;
import java.util.*;

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
