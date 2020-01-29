package com.epam.esm.generator;

import com.epam.esm.config.GeneratorConfig;

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
import java.util.concurrent.atomic.AtomicBoolean;

public class GeneratorManager {

    private BlockingQueue<File> allFolderCreated;
    private GeneratorConfig generatorConfig;
    private ExecutorService executorService;
    private Long localSubfolderCount;
    private AtomicBoolean timeout;

    public GeneratorManager(ResourceBundle generatorConfigProperties) {
        this.generatorConfig = new GeneratorConfig(generatorConfigProperties);
        this.allFolderCreated = new LinkedBlockingQueue<>();
        this.localSubfolderCount = this.generatorConfig.getSubfolderCount();
        this.timeout = new AtomicBoolean(false);
    }

    public BlockingQueue<File> getAllFolderCreated() {
        return allFolderCreated;
    }

    public void createFolders(String rootPath, long lvl) throws IOException {
        if (lvl == 0 || this.localSubfolderCount <= 0) {
            return;
        }
        String format = "LEVEL_%d_%d";
        long max = ThreadLocalRandom.current().nextLong(this.localSubfolderCount) + 1;
        for (int j = 0; j < max; j++) {
            String filename = String.format(format, lvl, j);
            String pathStr = rootPath + File.separator + filename;
            Path path = Paths.get(pathStr);
            Files.createDirectories(path);
            System.out.println(Thread.currentThread() + " created folder: " + path.toString());
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
        for (int i = 0; i < amountOfFolders; i++) {
            tasks.add(new JSONFileGeneratorTask(
                    this.allFolderCreated,
                    validFiles, this.generatorConfig));

        }
        this.executorService = Executors.newScheduledThreadPool(amountOfFolders);
        try {
            List<Future<Long>> futures = this.executorService.invokeAll(tasks);
            /*while (true) {
                boolean isDone = false;
                for (Future i : futures) {
                    if (i.isDone()) {
                        System.out.println("Future " + i + " is done, timing out");
                        this.timeout.set(true);
                        isDone = true;
                        break;
                    }
                }
                if (isDone) {
                    break;
                }
            }*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public GeneratorConfig getGeneratorConfig() {
        return generatorConfig;
    }

    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }
}
