package com.epam.esm.scanner;

import com.epam.esm.config.YAMLConfig;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.validator.ValidatorTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ScannerTask implements Runnable {

    static final Logger LOG = LoggerFactory.getLogger(ScannerTask.class);
    private static final Integer MAX_SIZE = 50;
    private ScannerManager manager;
    private ExecutorService globalPooledExecutors;
    private BlockingQueue<GiftCertificateDTO> validGiftCertificateDTOSToAddToDB;
    private YAMLConfig config;


    public ScannerTask(ScannerManager manager, YAMLConfig config) {
        this.manager = manager;
        this.config = config;
        this.globalPooledExecutors = Executors.newFixedThreadPool(config.getThreadCount());
        this.validGiftCertificateDTOSToAddToDB = new LinkedBlockingQueue<>(MAX_SIZE);
        LOG.debug("Scanner Task Instantiated");
    }

    @Override
    public void run() {
        LOG.debug("Started Scanner Task!");
        File[] filesandfolders = new File(config.getScanPath()).listFiles();
        try {
            List<ValidatorTask> validatorTasks = initialScan(filesandfolders);
            globalPooledExecutors.invokeAll(validatorTasks);
            LOG.debug("Invoked Validator Tasks!");
        } catch (FileNotFoundException | InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }

    }

    /*public void addFilesFromPath(BlockingQueue<File> files, Path path) {
        File file = path.toFile();
        if (file.isDirectory()) {

        } else {
            if (file.isFile() && file.exists() && file.canRead()) {
                files.add(file);
            }
        }

    }*/

    private List<ValidatorTask> initialScan(File[] filesandfolders) throws FileNotFoundException {
        LOG.debug("Initial scan started!");
        if (filesandfolders != null) {
            PriorityQueue<File> files = new PriorityQueue<>(new FileComparator());
            Collections.addAll(files, filesandfolders);
            int partSize = getPartSize(files.size(), MAX_SIZE);
            List<ValidatorTask> validatorTasks = initializeListOfValidators(config.getThreadCount(), partSize);
            for (int i = 0; i < validatorTasks.size(); i++) {
                if (!files.isEmpty()) {
                    File file = files.poll();
                    if (file != null) {
                        validatorTasks.get(i).getFilesToValidate().add(file);
                    }
                } else {
                    break;
                }
                if (i == validatorTasks.size() - 1) {
                    i = 0;
                }
            }
            return validatorTasks;
        }
        throw new FileNotFoundException("Error scanning scan directory");
    }

    private Integer getPartSize(Integer listSize, Integer maxSize) {
        return listSize % maxSize == 0 ? listSize / maxSize : listSize / maxSize + 1;
    }

    private List<ValidatorTask> initializeListOfValidators(Integer size, Integer queueSize) {
        List<ValidatorTask> validatorTasks = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            validatorTasks.add(new ValidatorTask(new LinkedBlockingQueue<>(queueSize), this));
        }
        return validatorTasks;
    }

    private void addValidCertificateToDTO(GiftCertificateDTO dto) {
        this.validGiftCertificateDTOSToAddToDB.add(dto);
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


}
