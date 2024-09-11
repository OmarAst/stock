package com.inventory.stock.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ConfigDataSource {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String pass;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int poolMax;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private int poolMin;

    @Value("${spring.datasource.hikari.idle-timeout}")
    private int idleTimeout;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private int connectionTimeout;

    @Value("${spring.datasource.hikari.max-lifetime}")
    private int maxLifetime;

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(pass);
        config.setDriverClassName(driver);

        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMinimumIdle(poolMin);
        config.setMaximumPoolSize(poolMax);
        config.setMaxLifetime(maxLifetime);

        return new HikariDataSource(config);
    }

    @Primary
    @Bean(name = "datasourceStock")
    public DataSource stockDataSource() {
        return createDataSource();
    }

    @Bean(name = "jdbcStock")
    public JdbcTemplate jdbcStock(@Qualifier("datasourceStock") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManagerStock(@Qualifier("datasourceStock") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
