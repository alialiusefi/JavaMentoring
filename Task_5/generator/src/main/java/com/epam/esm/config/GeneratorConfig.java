package com.epam.esm.config;

import java.util.ResourceBundle;

public class GeneratorConfig {

    private String path;
    private Long subfolderCount;
    private Long testTime;
    private Long periodTime;
    private Long filesCount;

    public GeneratorConfig(ResourceBundle bundle) {
        this.path = bundle.getString("path");
        this.subfolderCount = Long.parseLong(bundle.getString("subfolderCount"));
        this.testTime = Long.parseLong(bundle.getString("testTime"));
        this.periodTime = Long.parseLong(bundle.getString("periodTime"));
        this.filesCount = Long.parseLong(bundle.getString("filesCount"));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSubfolderCount() {
        return subfolderCount;
    }

    public void setSubfolderCount(Long subfolderCount) {
        this.subfolderCount = subfolderCount;
    }

    public Long getTestTime() {
        return testTime;
    }

    public void setTestTime(Long testTime) {
        this.testTime = testTime;
    }

    public Long getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(Long periodTime) {
        this.periodTime = periodTime;
    }

    public Long getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(Long filesCount) {
        this.filesCount = filesCount;
    }


}
