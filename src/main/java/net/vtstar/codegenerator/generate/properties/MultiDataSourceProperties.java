package net.vtstar.codegenerator.generate.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.datasource")
public class MultiDataSourceProperties {

    @Getter
    @Setter
    private boolean enabled;

    @Getter
    private final Map<String, DataSourceProperties> map = new HashMap<>();
}
