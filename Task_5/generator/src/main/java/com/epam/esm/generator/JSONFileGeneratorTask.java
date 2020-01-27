package com.epam.esm.generator;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class JSONFileGeneratorTask implements Callable<Long> {

    private BlockingQueue<File> folderPaths;
    private Long validFiles;
    private Long wrongJSONFormat;
    private Long wrongFieldNames;
    private Long invalidBean;
    private Long dbConstraintViolation;

    public JSONFileGeneratorTask(BlockingQueue<File> folderPaths, Long validFiles,
                                 Long wrongJSONFormat, Long wrongFieldNames, Long invalidBean, Long dbConstraintViolation) {
        this.folderPaths = folderPaths;
        this.validFiles = validFiles;
        this.wrongJSONFormat = wrongJSONFormat;
        this.wrongFieldNames = wrongFieldNames;
        this.invalidBean = invalidBean;
        this.dbConstraintViolation = dbConstraintViolation;
    }

    @Override
    public Long call() throws Exception {
        return null;
    }
}
