package com.epam.esm.config;


import com.java.esm.entity.Entity;
import com.java.esm.entity.GiftCertificate;
import com.java.esm.pool.ConnectionPool;
import com.java.esm.repository.CRUDRepo;
import com.java.esm.repository.GiftCertificateRepo;
import com.java.esm.repository.TagRepo;
import com.java.esm.repository.rowmapper.GiftCertificateRowMapper;
import com.java.esm.repository.rowmapper.TagRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
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

    @Bean
    public GiftCertificateRepo giftCertificateRepo(JdbcTemplate template) {
        return new GiftCertificateRepo(template, new GiftCertificateRowMapper());
    }

    @Bean
    public TagRepo tagRepo(JdbcTemplate template) {
        return new TagRepo(template, new TagRowMapper());
    }
}
