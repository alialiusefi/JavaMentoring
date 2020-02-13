package com.epam.esm.generator;

import com.epam.esm.config.GeneratorConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class JSONFileGeneratorTask implements Callable<Long> {


    private BlockingQueue<File> folderPaths;
    private Long validFilesPerFolder;
    private GeneratorConfig config;
    private ScheduledExecutorService executorService;
    private Logger LOG = LogManager.getLogger(JSONFileGeneratorTask.class);
    private Long invalidFilesPerFolder;
    public static AtomicLong actualAmountOfValidFiles = new AtomicLong(0);
    public static AtomicLong actualAmountOfInvalidFiles = new AtomicLong(0);

    public JSONFileGeneratorTask(BlockingQueue<File> folderPaths, Long validFilesPerFolder, Long invalidFilesPerFolder, GeneratorConfig config) {
        this.folderPaths = folderPaths;
        this.validFilesPerFolder = validFilesPerFolder;
        this.invalidFilesPerFolder = invalidFilesPerFolder;
        this.config = config;
        this.executorService = Executors.newScheduledThreadPool(1);
    }


    @Override
    public Long call() throws Exception {
        while (!folderPaths.isEmpty()) {
            File folderToPopulate = folderPaths.poll();
            ScheduledFuture scheduledFuture = executorService.scheduleAtFixedRate(
                    new JSONFileGeneratorPeriodTask(folderToPopulate, validFilesPerFolder, config, invalidFilesPerFolder),
                    10, config.getPeriodTime(), TimeUnit.MILLISECONDS);
            ScheduledExecutorService timerService = Executors.newScheduledThreadPool(1);
            timerService.schedule(() -> {
                scheduledFuture.cancel(true);
                LOG.info(
                        "\n|----------AFTER GENERATION--------|" +
                                "\nActual amount of total valid files: " + actualAmountOfValidFiles.get() +
                                "\nActual amount of total invalid files: " + actualAmountOfInvalidFiles.get() +
                                "\nActual amount of total valid certificates: " + actualAmountOfValidFiles.get() * 3
                );
                executorService.shutdown();
                try {
                    if (!executorService.awaitTermination(config.getTestTime(), TimeUnit.SECONDS)) {
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executorService.shutdownNow();
                }
            }, config.getTestTime(), TimeUnit.SECONDS);
        }
        return 1l;
    }
}
