package com.epam.esm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("scanner")
@EnableConfigurationProperties
public class YAMLConfig {

    private Long scanDelay;

    private Integer threadCount;

    private String scanPath;

    private String errorPath;

    public Long getScanDelay() {
        return scanDelay;
    }

    public void setScanDelay(Long scanDelay) {
        this.scanDelay = scanDelay;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public String getScanPath() {
        return scanPath;
    }

    public void setScanPath(String scanPath) {
        this.scanPath = scanPath;
    }

    public String getErrorPath() {
        return errorPath;
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }
}
