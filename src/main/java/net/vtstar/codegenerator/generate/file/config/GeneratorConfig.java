package net.vtstar.codegenerator.generate.file.config;

import net.vtstar.codegenerator.generate.file.properties.GeneratorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("pro")
@Configuration
@EnableConfigurationProperties(GeneratorProperties.class)
public class GeneratorConfig {
}
