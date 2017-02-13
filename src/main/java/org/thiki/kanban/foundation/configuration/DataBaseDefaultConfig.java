package org.thiki.kanban.foundation.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by xubt on 13/02/2017.
 */
@Configuration
@ComponentScan
@MapperScan("org.thiki")
@ConditionalOnProperty(name = "druid.enabled", havingValue = "false")
public class DataBaseDefaultConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource mysql = new DriverManagerDataSource();
        mysql.setDriverClassName(driver);
        mysql.setUrl(url);
        mysql.setUsername(userName);
        mysql.setPassword(password);
        return mysql;
    }
}
