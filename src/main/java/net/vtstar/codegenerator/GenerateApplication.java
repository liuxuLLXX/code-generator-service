package net.vtstar.codegenerator;

import net.vtstar.user.autoconfiguration.UserAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        MybatisAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        UserAutoConfiguration.class,
        SecurityAutoConfiguration.class})
public class GenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenerateApplication.class, args);
    }

}
