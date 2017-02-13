package org.thiki.kanban.foundation.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by xubt on 13/02/2017.
 */
@Configuration
@ComponentScan
@MapperScan("org.thiki")
@ConditionalOnProperty("druid.enabled")
public class DruidConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;
    @Value("${datasource.initialSize}")
    private int initialSize;
    @Value("${datasource.maxActive}")
    private int maxActive;
    @Value("${datasource.minIdle}")
    private int minIdle;
    @Value("${datasource.maxWait}")
    private int maxWait;
    @Value("${datasource.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${datasource.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;
    @Value("${datasource.validationQuery}")
    private String validationQuery;
    @Value("${datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${datasource.filters}")
    private String filters;
    @Value("${datasource.connectionProperties}")
    private String connectionProperties;
    @Value("${datasource.druidUserName}")
    private String druidUserName;
    @Value("${datasource.druidPassword}")
    private String druidPassword;

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.userName);
        druidDataSource.setPassword(this.password);
        druidDataSource.setDriverClassName(this.driver);
        druidDataSource.setInitialSize(this.initialSize);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setMinIdle(this.minIdle);
        druidDataSource.setMaxWait(this.maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(this.validationQuery);
        druidDataSource.setTestWhileIdle(this.testWhileIdle);
        druidDataSource.setTestOnBorrow(this.testOnBorrow);
        druidDataSource.setTestOnReturn(this.testOnReturn);
        druidDataSource.setPoolPreparedStatements(this.poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);
        try {
            druidDataSource.setFilters(this.filters);
        } catch (Exception e) {
            throw new RuntimeException("load datasource error, dbProperties is :", e);
        }

        try {
            if (null != druidDataSource) {
                druidDataSource.setFilters("wall,stat");
                druidDataSource.setUseGlobalDataSourceStat(true);
                druidDataSource.init();
            }
        } catch (Exception e) {
            throw new RuntimeException("load datasource error, dbProperties is :", e);
        }

        return druidDataSource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        //reg.addInitParameter("allow", "127.0.0.1"); //白名单
        //reg.addInitParameter("deny",""); //黑名单
        reg.addInitParameter("loginUsername", druidUserName);
        reg.addInitParameter("loginPassword", druidPassword);
        return reg;
    }
}
