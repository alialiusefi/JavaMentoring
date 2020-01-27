package com.epam.esm.scanner;

import com.epam.esm.config.YAMLConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ScannerManager {

    static final Logger LOG = LoggerFactory.getLogger(ScannerManager.class);
    private ExecutorService executorService;
    private YAMLConfig config;
    private ScheduledFuture scheduledFuture;
    @Autowired
    private Environment environment;

    public ScannerManager(YAMLConfig config) {
        this.config = config;
        this.executorService = Executors.newScheduledThreadPool(1);
    }

    @PostConstruct
    public void init() {
        LOG.debug("Starting async json file processing");
        ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) this.executorService;
        ScannerTask scannerTask = new ScannerTask(this, config);
        scheduledExecutorService.scheduleAtFixedRate(scannerTask,
                config.getScanDelay(), config.getScanDelay(), TimeUnit.MILLISECONDS);
        //scheduledExecutorService.shutdown();
    }

    void cancelScheduledExecutorService() {
        this.scheduledFuture.cancel(true);
    }

}
