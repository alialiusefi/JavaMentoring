package com.epam.esm.scanner;

import com.epam.esm.config.YAMLConfig;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.ValidatorTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ScannerTask implements Runnable {

    static final Logger LOG = LoggerFactory.getLogger(ScannerTask.class);
    private ScannerManager manager;
    private ExecutorService globalPooledExecutors;
    private YAMLConfig config;
    private GiftCertificateService giftCertificateService;

    public ScannerTask(ScannerManager manager, YAMLConfig config, GiftCertificateService giftCertificateService) {
        this.manager = manager;
        this.config = config;
        this.globalPooledExecutors = Executors.newFixedThreadPool(config.getThreadCount());
        this.giftCertificateService = giftCertificateService;
        LOG.debug("Scanner Task Instantiated");
    }

    @Override
    public void run() {
        try {

            LOG.debug(Thread.currentThread() + " Started Scanner Task!");
            File[] filesandfolders = new File(config.getScanPath()).listFiles();
            List<ValidatorTask> validatorTasks = initialScan(filesandfolders);
            globalPooledExecutors.invokeAll(validatorTasks);
            LOG.info("STATISTICS: Amount of certificates in db after validators invoked: "
                    + giftCertificateService.getCountAllGiftCertificates());
            LOG.info("STATISTICS: Amount of files in giftcertificates after validators invoked: "
                    + fileCount(Paths.get(config.getScanPath())));
            LOG.info("STATISTICS: Amount of files in error after validators invoked: "
                    + fileCount(Paths.get(config.getErrorPath())));
        } catch (IOException | InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }

    }

    public YAMLConfig getConfig() {
        return config;
    }

    public void setConfig(YAMLConfig config) {
        this.config = config;
    }

    private List<ValidatorTask> initialScan(File[] filesandfolders) throws IOException {
        LOG.debug("Initial scan started!");
        if (filesandfolders.length == 0) {
            LOG.error("Empty directory");
            throw new IOException("Empty directory");
        }
        if (filesandfolders != null) {
            PriorityQueue<File> files = new PriorityQueue<>(new FileComparator());
            Collections.addAll(files, filesandfolders);
            LOG.debug("Amount of initial scan files: " + files.size());
            int partSize = getPartSize(files.size(), config.getThreadCount());
            LOG.debug("Part size " + partSize);
            List<ValidatorTask> validatorTasks = initializeListOfValidators(config.getThreadCount());
            for (int i = 0; i < validatorTasks.size(); i++) {
                if (!files.isEmpty()) {
                    File file = files.poll();
                    if (file != null) {
                        LOG.debug("Adding " + file + " to " + validatorTasks.get(i) + "," + "files left to distribute:" + files.size());
                        validatorTasks.get(i).getFilesToValidate().add(file);
                    }
                } else {
                    break;
                }
                if (i == (validatorTasks.size() - 1)) {
                    i = -1;
                }
            }
            return validatorTasks;
        }
        throw new FileNotFoundException("Error scanning scan directory");
    }

    private Integer getPartSize(Integer listSize, Integer threadCount) {
        return listSize % threadCount == 0 ? listSize / threadCount : listSize / threadCount + 1;
    }

    private List<ValidatorTask> initializeListOfValidators(Integer size) {
        List<ValidatorTask> validatorTasks = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            validatorTasks.add(new ValidatorTask(this, giftCertificateService));
        }
        return validatorTasks;
    }



    private static class FileComparator implements Comparator<File> {
        @Override
        public int compare(File o1, File o2) {
            if (o1.isFile() && o2.isFile()) {
                return 0;
            }
            if (o1.isDirectory() && o2.isDirectory()) {
                return 0;
            }
            if (o1.isFile() && o2.isDirectory()) {
                return 1;
            }
            if (o1.isDirectory() && o2.isFile()) {
                return -1;
            }
            return 0;
        }
    }

    public long fileCount(Path dir) throws IOException {
        return Files.walk(dir)
                .parallel()
                .filter(p -> !p.toFile().isDirectory())
                .count();
    }

}
