package com.epam.esm.config;


import com.java.esm.pool.ConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.esm")
public class WebConfig {

    @Bean
    public ConnectionPool dataSource() {
        return ConnectionPool.getInstance();
    }

}
