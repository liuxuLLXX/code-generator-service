package net.vtstar.generate.config;

import net.vtstar.generate.properties.MultiDataSourceProperties;
import net.vtstar.generate.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnProperty(prefix = "spring.datasource", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(MultiDataSourceProperties.class)
public class DataSourceConfig {

    @Autowired
    private MultiDataSourceProperties properties;

    @Bean
    MapDataSourceLookup dataSourceLookup() {
        Map<String, DataSourceProperties> map = properties.getMap();
        if (!map.containsKey(ConstantsUtils.TENANT_DEFAULT)) {
            throw new IllegalArgumentException("默认[" + ConstantsUtils.TENANT_DEFAULT + "]数据源未配置");
        }
        Map<String, DataSource> dataSourceMap = map.entrySet().parallelStream().collect(Collectors.toMap(
                e -> e.getKey(),
                e -> e.getValue().initializeDataSourceBuilder().type(e.getValue().getType()).build()));
        return new MapDataSourceLookup(dataSourceMap);
    }

    @Primary
    @Bean
    DynamicDataSource dynamicDataSource(MapDataSourceLookup dsl) {
        DynamicDataSource ds = new DynamicDataSource();
        ds.setDataSourceLookup(dsl);
        ds.setTargetDataSources(dsl.getDataSources().entrySet().parallelStream().collect(Collectors.toMap(
                e -> e.getKey(),
                e -> e.getValue())));
        ds.setDefaultTargetDataSource(dsl.getDataSources().get(ConstantsUtils.TENANT_DEFAULT));
        return ds;
    }

}
