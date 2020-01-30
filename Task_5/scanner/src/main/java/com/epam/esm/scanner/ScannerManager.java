package com.epam.esm.scanner;

import com.epam.esm.config.YAMLConfig;
import com.epam.esm.service.GiftCertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScannerManager {

    static final Logger LOG = LoggerFactory.getLogger(ScannerManager.class);
    private ExecutorService executorService;
    private YAMLConfig config;
    private GiftCertificateService giftCertificateService;

    @Autowired
    private Environment environment;

    public ScannerManager(YAMLConfig config, GiftCertificateService giftCertificateService) {
        this.config = config;
        this.executorService = Executors.newScheduledThreadPool(1);
        this.giftCertificateService = giftCertificateService;
    }


    @PostConstruct
    public void init() {
        LOG.debug("Starting async json file processing");
        ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) this.executorService;
        ScannerTask scannerTask = new ScannerTask(this, config, giftCertificateService);
        scheduledExecutorService.scheduleAtFixedRate(scannerTask,
                0, config.getScanDelay(), TimeUnit.MILLISECONDS);
    }


}
