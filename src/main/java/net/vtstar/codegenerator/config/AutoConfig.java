package net.vtstar.codegenerator.config;

import net.vtstar.user.autoconfiguration.UserAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Auther: liuxu
 * @Date: 2019/3/12
 * @Description:
 */
@Configuration
@ConditionalOnProperty(prefix = "vtstar.generator", name = "mode", havingValue = "pro")
public class AutoConfig {

    @Import({DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class, UserAutoConfiguration.class})
    @MapperScan(basePackages = "net.vtstar.codegenerator")
    @ComponentScans(@ComponentScan(basePackages = "net.vtstar.codegenerator"))
    static class AutoConfigImportUser {

    }

}
