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

public class JSONFileGeneratorTask implements Callable<Long> {


    private BlockingQueue<File> folderPaths;
    private Long validFiles;
    private GeneratorConfig config;
    private ScheduledExecutorService executorService;
    private Logger LOG = LogManager.getLogger(JSONFileGeneratorTask.class);

    public JSONFileGeneratorTask(BlockingQueue<File> folderPaths, Long validFiles, GeneratorConfig config) {
        this.folderPaths = folderPaths;
        this.validFiles = validFiles;
        this.config = config;
        this.executorService = Executors.newScheduledThreadPool(1);
    }

    @Override
    public Long call() throws Exception {
        while (!folderPaths.isEmpty()) {
            LOG.debug(Thread.currentThread() + " has been started!");
            File folderToPopulate = folderPaths.poll();
            ScheduledFuture scheduledFuture = executorService.scheduleAtFixedRate(
                    new JSONFileGeneratorPeriodTask(folderToPopulate, validFiles, config),
                    0, config.getPeriodTime(), TimeUnit.MILLISECONDS);
            ScheduledExecutorService timerService = Executors.newScheduledThreadPool(1);
            timerService.schedule(() -> {
                scheduledFuture.cancel(true);
                executorService.shutdown();
                try {
                    if (!executorService.awaitTermination(config.getTestTime(), TimeUnit.MILLISECONDS)) {
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executorService.shutdownNow();
                }
            }, config.getTestTime(), TimeUnit.SECONDS);
        }
        LOG.debug(Thread.currentThread() + " JSON File Generator Task finished!");
        return 1l;
    }
}
