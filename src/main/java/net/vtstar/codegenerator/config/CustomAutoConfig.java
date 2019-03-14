package net.vtstar.codegenerator.config;

import net.vtstar.user.autoconfiguration.UserAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * @Auther: liuxu
 * @Date: 2019/3/12
 * @Description:
 */
@Profile("pro")
@Configuration
@Import({
        DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class,
        SecurityAutoConfiguration.class,
        UserAutoConfiguration.class})
@MapperScan("net.vtstar.codegenerator.record")
public class CustomAutoConfig {
}
