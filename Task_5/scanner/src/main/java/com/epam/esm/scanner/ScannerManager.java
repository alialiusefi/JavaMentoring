package com.epam.esm.scanner;

import com.epam.esm.config.YAMLConfig;
import com.epam.esm.service.GiftCertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        try {
            LOG.info("|============STATISTICS============|");
            LOG.info("|============BEFORE-SCAN===========|" +
                    "\n$ Amount of certificates in db before scanning: " + giftCertificateService.getCountAllGiftCertificates() +
                    "\n$ Amount of files in /giftcertificates before scanning: " + fileCount(Paths.get(config.getScanPath())) +
                    "\n$ Amount of files in /error before scanning: " + fileCount(Paths.get(config.getErrorPath())) +
                    "Starting async json file processing");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) this.executorService;
        ScannerTask scannerTask = new ScannerTask(this, config, giftCertificateService);
        scheduledExecutorService.scheduleAtFixedRate(scannerTask,
                0, config.getScanDelay(), TimeUnit.MILLISECONDS);
        ScheduledExecutorService statisticsAfterScanService = Executors.newScheduledThreadPool(1);
        statisticsAfterScanService.scheduleAtFixedRate(() -> {
            try {
                if (fileCount(Paths.get(config.getScanPath())) == 0) {
                    LOG.info("|============AFTER-SCAN===========|" +
                            "\n$ Amount of certificates in db after scanning: " + giftCertificateService.getCountAllGiftCertificates()
                            + "\n$ Amount of files in /giftcertificates after scanning: " + fileCount(Paths.get(config.getScanPath()))
                            + "\n$ Amount of files in /error after scanning: " + fileCount(Paths.get(config.getErrorPath())));
                }
            } catch (IOException e) {
                LOG.debug(e.getMessage());
            }
        }, config.getScanDelay(), config.getScanDelay(), TimeUnit.MILLISECONDS);

    }

    public long fileCount(Path dir) throws IOException {
        return Files.walk(dir)
                .parallel()
                .filter(p -> !p.toFile().isDirectory())
                .count();
    }
}
