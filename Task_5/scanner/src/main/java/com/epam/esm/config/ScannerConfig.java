package com.epam.esm.config;

import com.epam.esm.scanner.ScannerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
public class ScannerConfig {

    @Bean
    public ScannerManager scannerManager(YAMLConfig config) {
        return new ScannerManager(config);
    }

}
