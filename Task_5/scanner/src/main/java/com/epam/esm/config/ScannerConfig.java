package com.epam.esm.config;

import com.epam.esm.scanner.ScannerManager;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
public class ScannerConfig {

    @Bean
    public ScannerManager scannerManager(YAMLConfig config, @Autowired GiftCertificateService giftCertificateService) {
        return new ScannerManager(config, giftCertificateService);
    }


}
