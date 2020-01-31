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
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class GeneratorManager {

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
        if (lvl == 0 || this.localSubfolderCount <= 0) {
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

    public void start() {
        List<JSONFileGeneratorTask> tasks = new ArrayList<>();
        int amountOfFolders = allFolderCreated.size();
        Long validFiles = generatorConfig.getFilesCount() * 16;
        Long invalidFiles = generatorConfig.getFilesCount();
        LOG.info("STATISTICS:Expected amount of valid files per folder:" + validFiles);
        LOG.info("STATISTICS:Expected amount of total valid files:" + validFiles * amountOfFolders * (generatorConfig.getTestTime() /
                (generatorConfig.getPeriodTime() / 1000)));
        LOG.info("STATISTICS:Expected amount of total invalid files:" + 4 * invalidFiles * amountOfFolders * (generatorConfig.getTestTime() /
                (generatorConfig.getPeriodTime() / 1000)));
        for (int i = 0; i < amountOfFolders; i++) {
            tasks.add(new JSONFileGeneratorTask(
                    this.allFolderCreated,
                    validFiles, this.generatorConfig));

        }
        this.executorService = Executors.newScheduledThreadPool(amountOfFolders);
        try {
            List<Future<Long>> futures = this.executorService.invokeAll(tasks);
            this.executorService.shutdown();
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
