package com.epam.esm.generator;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class JSONFileGeneratorTask implements Callable<Long> {

    private BlockingQueue<File> folderPaths;
    private Long validFiles;


    public JSONFileGeneratorTask(BlockingQueue<File> folderPaths, Long validFiles) {
        this.folderPaths = folderPaths;
        this.validFiles = validFiles;

    }

    @Override
    public Long call() throws Exception {
        while (!folderPaths.isEmpty()) {
            File folderToPopulate = folderPaths.poll(10, TimeUnit.SECONDS);

        }
        return 1l;
    }
}
