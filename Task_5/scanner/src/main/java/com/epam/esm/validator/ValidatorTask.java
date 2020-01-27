package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.scanner.ScannerTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ValidatorTask implements Callable<Long> {

    static final Logger LOG = LoggerFactory.getLogger(ValidatorTask.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private BlockingQueue<File> filesToValidate;
    private ScannerTask scannerTask;

    public ValidatorTask(BlockingQueue<File> filesToValidate, ScannerTask scannerTask) {
        this.filesToValidate = filesToValidate;
        this.scannerTask = scannerTask;
    }

    public BlockingQueue<File> getFilesToValidate() {
        return filesToValidate;
    }

    public void setFilesToValidate(BlockingQueue<File> filesToValidate) {
        this.filesToValidate = filesToValidate;
    }

    public ScannerTask getScannerTask() {
        return scannerTask;
    }

    public void setScannerTask(ScannerTask scannerTask) {
        this.scannerTask = scannerTask;
    }

    @Override
    public Long call() throws Exception {
        LOG.debug("Thread : " + Thread.currentThread() + " started!");
        Long amountOfCertificates = 0L;
        while (!filesToValidate.isEmpty()) {
            File file = filesToValidate.poll(5, TimeUnit.SECONDS);
            List<GiftCertificateDTO> validDtos = getListOfValidCertificates(file);
            amountOfCertificates += validDtos.size();
        }
        return amountOfCertificates;
    }

    private List<GiftCertificateDTO> getListOfValidCertificates(File file) throws IOException {
        try {
            Object objects = objectMapper.readValue(file, GiftCertificateDTO.class);
            if (objects instanceof List) {
                List objectList = (List) objects;
                return objectList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IOException("Cannot convert to list of objects");
    }


}
