package net.vtstar.codegenerator.generate.file.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;


@Profile("pro")
@ConfigurationProperties(prefix = "genenrator")
public class GeneratorProperties {

    @Getter
    @Setter
    private String genenratorPath;

}
