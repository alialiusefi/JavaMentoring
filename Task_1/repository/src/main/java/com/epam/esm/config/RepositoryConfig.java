package com.epam.esm.config;


import com.epam.esm.pool.ConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})

public class RepositoryConfig {

    @Bean
    public DataSource getDataSource() {
        ResourceBundle bundle = ResourceBundle.getBundle("dbConfig");
        ConnectionPool.getInstance().initialize(
                bundle.getString("driver"),
                bundle.getString("URL"),
                bundle.getString("username"),
                bundle.getString("password"),
                Integer.parseInt(bundle.getString("initConnections")),
                Integer.parseInt(bundle.getString("maxConnections")),
                Integer.parseInt(bundle.getString("timeout"))
        );
        return ConnectionPool.getInstance();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
