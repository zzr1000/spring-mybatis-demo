package org.zzr1000.springTest.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多个数据源配置
 * xiaoxian20180529
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "studentInfo")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.studentInfo") // application.properteis中对应属性的前缀
    public DataSource dataSourceTalent() {
        return DataSourceBuilder.create().build();
    }
}