package net.vtstar.generate.service;

import lombok.extern.slf4j.Slf4j;
import net.vtstar.generate.config.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@ConditionalOnProperty(prefix = "spring.datasource", name = "enabled", havingValue = "true")
@Service
public class DynamicDataSourceService {

    @Autowired
    private MapDataSourceLookup lookup;
    @Autowired
    private DynamicDataSource dds;

    public void addDataSource(DataSourceProperties properties) {
        DataSource dataSource = properties.initializeDataSourceBuilder()
                .type(properties.getType())
                .build();
        log.info("add dataSource {} -> {}", properties.getName(), dataSource.getClass() + "@" + dataSource.hashCode());
        lookup.addDataSource(properties.getName(), dataSource);
        dds.afterPropertiesSet();
    }

    public Map<String, DataSource> getDataSources() {
        return lookup.getDataSources();
    }
}
