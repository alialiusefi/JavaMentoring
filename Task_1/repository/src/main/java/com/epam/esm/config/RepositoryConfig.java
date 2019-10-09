package com.epam.esm.config;


import com.epam.esm.pool.ConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})

public class RepositoryConfig {

    @Bean
    public DataSource dataSource() {
        ResourceBundle bundle = ResourceBundle.getBundle("dbConfig");
        return new ConnectionPool(bundle.getString("driver"),
                bundle.getString("URL"),
                bundle.getString("username"),
                bundle.getString("password"),
                Integer.parseInt(bundle.getString("initConnections")),
                Integer.parseInt(bundle.getString("maxConnections")),
                Integer.parseInt(bundle.getString("timeout")));
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @DependsOn("ConnectionPool")
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
