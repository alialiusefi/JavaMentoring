package com.epam.esm.generator;

import com.epam.esm.config.GeneratorConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class GeneratorManager {

    private BlockingQueue<File> allFolderCreated;
    private GeneratorConfig generatorConfig;
    private ExecutorService executorService;

    public GeneratorManager(ResourceBundle generatorConfigProperties) {
        this.generatorConfig = new GeneratorConfig(generatorConfigProperties);
        this.allFolderCreated = new LinkedBlockingQueue<>();
    }

    public void createFolders() {
        Long averageDepth = generatorConfig.getSubfolderCount() / 3;

        String level = "LEVEL";
        for (int i = 0; i < averageDepth; i++) {
            for (int j = 0; j < generatorConfig.getSubfolderCount(); j++) {
                String folderName = level + "_" + i + "_" + j;
                File folderPath = new File(generatorConfig.getPath() + folderName);
                if (!folderPath.isDirectory()) {
                    boolean isCreatedFolder = folderPath.mkdir();
                    if (!isCreatedFolder) {
                        System.out.println(folderName + " wasn't created!");
                        continue;
                    }
                    allFolderCreated.add(folderPath);
                }
            }
        }
    }

    private void createFolderRecursive(String path, long subfolderCount, long level, long avgDepth) {
        if (level == avgDepth) {
            return;
        }
        String currentPath = path;
        String newPath = currentPath + "_" + level;

        long newLevel = level++;

    }

    public void start() {
        List<JSONFileGeneratorTask> tasks = new ArrayList<>();
        int amountOfFolders = allFolderCreated.size();
        Long validFiles = generatorConfig.getFilesCount() * 16;
        for (int i = 0; i < amountOfFolders; i++) {
            tasks.add(new JSONFileGeneratorTask(
                    this.allFolderCreated,
                    validFiles,
                    generatorConfig.getFilesCount(),
                    generatorConfig.getFilesCount(),
                    generatorConfig.getFilesCount(),
                    generatorConfig.getFilesCount()));

        }
        this.executorService = Executors.newScheduledThreadPool(amountOfFolders);
        try {
            this.executorService.invokeAll(tasks, generatorConfig.getTestTime(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
