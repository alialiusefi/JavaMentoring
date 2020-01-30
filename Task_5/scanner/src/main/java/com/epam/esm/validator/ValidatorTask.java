package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.scanner.ScannerTask;
import com.epam.esm.service.GiftCertificateService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ValidatorTask implements Callable<Long> {

    static final Logger LOG = LoggerFactory.getLogger(ValidatorTask.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String WRONGJSONFORMAT = "wrongjsonformat";
    private static final String WRONGFIELDNAMES = "wrongfieldname";
    private static final String WRONGBEAN = "wrongbean";
    private static final String DBCONSTRAINTVIOLATION = "dbconstraintviolation";
    private LinkedList<File> filesToValidate = new LinkedList<>();
    private ScannerTask scannerTask;
    private ReentrantLock reentrantLock = new ReentrantLock();

    private GiftCertificateService service;

    public ValidatorTask(ScannerTask scannerTask, GiftCertificateService giftCertificateService) {
        this.scannerTask = scannerTask;
        this.service = giftCertificateService;
    }

   /* @Autowired
    public void setService(GiftCertificateService service) {
        this.service = service;
    }*/

    public LinkedList<File> getFilesToValidate() {
        return filesToValidate;
    }

    public void setFilesToValidate(LinkedList<File> filesToValidate) {
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
        LOG.debug("Validator Thread : " + Thread.currentThread() + " started!");
        Long amountOfCertificates = 0L;
        while (!filesToValidate.isEmpty()) {
            File file = filesToValidate.poll();
            if (file.isDirectory()) {
                File[] filesInDirectory = file.listFiles();
                try {
                    filesToValidate.addAll(Arrays.asList(filesInDirectory));
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            } else {
                LOG.info(Thread.currentThread() + " is now validating file: " + file);
                List<GiftCertificateDTO> validDtos = getListOfDTOS(file);
                try {
                    if (!validDtos.isEmpty()) {
                        String filepath = file.getAbsolutePath();
                        LOG.info(filepath + " is valid!");
                        try {
                            validDtos.forEach(service::add);
                            reentrantLock.lock();
                            LOG.debug(Thread.currentThread() + " is now deleting " + file.getAbsolutePath());
                            Files.delete(Paths.get(filepath));
                            reentrantLock.unlock();
                        } catch (DataIntegrityViolationException r) {
                            LOG.info(r.getMessage(), r);
                            moveFile(file.getAbsolutePath(), scannerTask.getConfig().getErrorPath() + File.separator + DBCONSTRAINTVIOLATION +
                                    File.separator + file.getName());
                        }
                    }
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        return amountOfCertificates;
    }

    private List<GiftCertificateDTO> getListOfDTOS(File file) throws IOException {
        String JSONList;
        try (FileReader fileReader = new FileReader(file)) {
            try (BufferedReader reader = new BufferedReader(fileReader)) {
                JSONList = reader.readLine();
            }
        }
        try {
            List<GiftCertificateDTO> dtos = objectMapper.readValue(JSONList,
                    new TypeReference<List<GiftCertificateDTO>>() {
                    });
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            for (GiftCertificateDTO i : dtos) {
                try {
                    Set set = validator.validate(i);
                    if (!set.isEmpty()) {
                        moveFile(file.getAbsolutePath(), scannerTask.getConfig().getErrorPath() + File.separator + WRONGBEAN +
                                File.separator + file.getName());
                        return new ArrayList<>();
                    }
                    return dtos;
                } catch (IllegalArgumentException | ValidationException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        } catch (UnrecognizedPropertyException e) {
            moveFile(file.getAbsolutePath(), scannerTask.getConfig().getErrorPath() + File.separator + WRONGFIELDNAMES +
                    File.separator + file.getName());

        } catch (JsonParseException | MismatchedInputException e) {
            moveFile(file.getAbsolutePath(), scannerTask.getConfig().getErrorPath() + File.separator + WRONGJSONFORMAT +
                    File.separator + file.getName());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    private void moveFile(String pathFrom, String pathTo) {
        reentrantLock.lock();
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        try {
            LOG.info(Thread.currentThread() + " is now moving " + from.toAbsolutePath() + " to " + to.toAbsolutePath());
            Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        reentrantLock.unlock();
    }
}


