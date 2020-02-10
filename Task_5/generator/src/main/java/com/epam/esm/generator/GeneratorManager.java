package com.epam.esm.generator;

import com.epam.esm.config.GeneratorConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class GeneratorManager {

    private static int MAX_AMOUNT_OF_THREADS = 20;
    private final String FORMAT = "LEVEL_%d_%d";
    private BlockingQueue<File> allFolderCreated;
    private GeneratorConfig generatorConfig;
    private ExecutorService executorService;
    private Long localSubfolderCount;
    private Logger LOG = LogManager.getLogger(GeneratorManager.class);

    public GeneratorManager(ResourceBundle generatorConfigProperties) {
        this.generatorConfig = new GeneratorConfig(generatorConfigProperties);
        this.allFolderCreated = new LinkedBlockingQueue<>();
        this.localSubfolderCount = this.generatorConfig.getSubfolderCount();
    }

    public BlockingQueue<File> getAllFolderCreated() {
        return allFolderCreated;
    }

    public void createFolders(String rootPath, long lvl) throws IOException {
        if (lvl <= 0 || this.localSubfolderCount <= 0) {
            return;
        }
        long max = ThreadLocalRandom.current().nextLong(this.localSubfolderCount) + 1;
        for (int j = 0; j < max; j++) {
            String folderName = String.format(FORMAT, lvl, j);
            String pathStr = rootPath + File.separator + folderName;
            Path path = Paths.get(pathStr);
            Files.createDirectories(path);
            LOG.debug(Thread.currentThread() + " created folder: " + path.toString());
            if (!allFolderCreated.contains(path.toFile())) {
                allFolderCreated.add(path.toFile());
            }
            this.localSubfolderCount = this.localSubfolderCount - max;
            createFolders(path.toString(), lvl - 1);
        }
    }

    public void validateAmountOfSubdirectoriesCreated(String rootPathStr, long lvl) throws IOException {
        List<Path> rootFolders = new LinkedList<>();
        List<Path> allFolders = new LinkedList<>();
        Path rootPath = Paths.get(rootPathStr);
        Files.walk(rootPath, (int) lvl).filter(p -> Files.isDirectory(p) && !p.equals(rootPath))
                .forEach(allFolders::add);
        Files.walk(rootPath, 1).filter(p -> Files.isDirectory(p) && !p.equals(rootPath))
                .forEach(rootFolders::add);
        if (allFolders.size() < this.generatorConfig.getSubfolderCount()) {
            for (int i = 0; i < this.generatorConfig.getSubfolderCount() - allFolders.size(); i++) {
                String folderName = String.format(FORMAT, lvl, rootFolders.size() + i);
                String pathStr = rootPath + File.separator + folderName;
                Path path = Paths.get(pathStr);
                Files.createDirectories(path);
            }
        }
    }

    public void start() {
        List<JSONFileGeneratorTask> tasks = new ArrayList<>();
        int amountOfFolders = allFolderCreated.size();
        Double validFiles = (double) generatorConfig.getFilesCount() * 16;
        Double invalidFiles = (double) generatorConfig.getFilesCount() * 4;
        LOG.info("STATISTICS: Expected amount of valid files per folder:" + validFiles);
        LOG.info("STATISTICS: Expected amount of invalid files per folder:" + invalidFiles);
        Double periodTimeMS = (double) generatorConfig.getPeriodTime();
        Double testTimeMS = (double) generatorConfig.getTestTime() * 1000;
        Double totalAmountOfValidFiles = validFiles * amountOfFolders * (testTimeMS / periodTimeMS);
        LOG.info("STATISTICS: Expected amount of total valid files:" + totalAmountOfValidFiles);
        Double totalAmountOfInvalidFiles = invalidFiles * amountOfFolders * (testTimeMS / periodTimeMS);
        LOG.info("STATISTICS: Expected amount of total invalid files:" + totalAmountOfInvalidFiles);
        LOG.info("STATISTICS: Expected amount of valid certificates that will be generated:" + totalAmountOfValidFiles * 3);
        LOG.info("STATISTICS: Expected amount of invalid certificates that will be generated:" + totalAmountOfInvalidFiles * 3);
        for (int i = 0; i < amountOfFolders; i++) {
            tasks.add(new JSONFileGeneratorTask(
                    this.allFolderCreated,
                    Math.round(validFiles), this.generatorConfig));
        }
        if (amountOfFolders > MAX_AMOUNT_OF_THREADS) {
            this.executorService = Executors.newFixedThreadPool(MAX_AMOUNT_OF_THREADS);
        } else {
            this.executorService = Executors.newFixedThreadPool(amountOfFolders);
        }
        try {
            List<Future<Long>> futures = this.executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }


    }

    public GeneratorConfig getGeneratorConfig() {
        return generatorConfig;
    }

    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }
}
