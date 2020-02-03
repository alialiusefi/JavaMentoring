package com.epam.esm.generator;

import com.epam.esm.config.GeneratorConfig;
import com.epam.esm.dto.DTO;
import com.epam.esm.factory.GiftCertificateFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;

public class JSONFileGeneratorPeriodTask implements Runnable {

    private static final long AMOUNT_OF_DTOS_PER_FILE = 3;
    private File folderToPopulate;
    private GiftCertificateFactory factory = new GiftCertificateFactory();
    private ObjectMapper mapper = new ObjectMapper();
    private Long validFiles;
    private GeneratorConfig config;
    private Logger LOG = LogManager.getLogger(JSONFileGeneratorPeriodTask.class);

    public JSONFileGeneratorPeriodTask(File folderToPopulate, Long validFiles, GeneratorConfig config) {
        this.folderToPopulate = folderToPopulate;
        this.validFiles = validFiles;
        this.config = config;
    }

    @Override
    public void run() {
        Long amountOfFilesWritten = 0l;
        LOG.debug(Thread.currentThread() + " will now begin populating folder: " + folderToPopulate.getAbsolutePath());
        LinkedList<DTO> giftCertificateDTO = new LinkedList<>();
        long amountOfValidDTOS = validFiles * AMOUNT_OF_DTOS_PER_FILE;
        long amountOfInvalidDTOS = config.getFilesCount() * AMOUNT_OF_DTOS_PER_FILE;
        giftCertificateDTO.addAll(factory.createValidJSONDTO(amountOfValidDTOS));
        giftCertificateDTO.addAll(factory.createIncorrectFieldCertificateDTOS(amountOfInvalidDTOS));
        giftCertificateDTO.addAll(factory.createDBConstraintViolationCertificate(amountOfInvalidDTOS));
        giftCertificateDTO.addAll(factory.createNonValidBeanCertificate(amountOfInvalidDTOS));
        while (!giftCertificateDTO.isEmpty()) {
            String filename = folderToPopulate.getAbsolutePath() + File.separator + UUID.randomUUID() + ".json";
            File file = new File(filename);
            StringBuilder stringbuilder = new StringBuilder("[");
            for (int i = 0; i < AMOUNT_OF_DTOS_PER_FILE; i++) {
                DTO dto = giftCertificateDTO.pop();
                String JSONStr = null;
                try {
                    JSONStr = mapper.writeValueAsString(dto);
                } catch (JsonProcessingException e) {
                    LOG.debug(e.getMessage(), e);
                }
                stringbuilder.append(JSONStr);
                if (i != AMOUNT_OF_DTOS_PER_FILE - 1) {
                    stringbuilder.append(",");
                }
            }
            stringbuilder.append("]");
            createAndWriteToFile(stringbuilder.toString(), file);
            amountOfFilesWritten++;
            LOG.debug(Thread.currentThread() + " has written current file: " + file.getAbsolutePath());
        }
        for (int i = 0; i < config.getFilesCount(); i++) {
            String filename = folderToPopulate.getAbsolutePath() + File.separator + UUID.randomUUID() + ".json";
            File file = new File(filename);
            createAndWriteToFile("{{{{", file);
            amountOfFilesWritten++;
            LOG.debug(Thread.currentThread() + " has written current file: " + file.getAbsolutePath());
        }
        LOG.info("STATISTICS:" + Thread.currentThread() + "wrote " + amountOfFilesWritten + " of files");
        LOG.debug(Thread.currentThread() + " generator stopped!");
    }

    private void createAndWriteToFile(String str, File file) {
        byte[] strBytes = str.getBytes();
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(strBytes);
            try {
                outputStream.flush();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }


}
