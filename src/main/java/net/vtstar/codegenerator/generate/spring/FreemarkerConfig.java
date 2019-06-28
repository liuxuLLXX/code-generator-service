package net.vtstar.codegenerator.generate.spring;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liuxu on 2019/02/21.
 *
 */
@Slf4j
@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {

    /**
     * freemarker Configuration.
     * @throws TemplateException freemarker 解析异常
     * @throws IOException io异常
     * @return freemarker configuration
     */
    @Bean
    @Primary
    public Configuration conf() throws TemplateException, IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setClassForTemplateLoading(FreemarkerConfig.class, "\\ftl");
        log.info("freemarker config ---> : get ftl success");

        try(InputStream is = getClass().getClassLoader().getResourceAsStream("config/freemarker.properties")){
            cfg.setSettings(is);
        }

        return cfg;
    }

}
