package com.epam.esm.generator;

import com.epam.esm.config.GeneratorConfig;
import com.epam.esm.dto.DTO;
import com.epam.esm.factory.GiftCertificateFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class JSONFileGeneratorTask implements Callable<Long> {

    private BlockingQueue<File> folderPaths;
    private Long validFiles;
    private GiftCertificateFactory factory = new GiftCertificateFactory();
    private GeneratorConfig config;

    public JSONFileGeneratorTask(BlockingQueue<File> folderPaths, Long validFiles, GeneratorConfig config) {
        this.folderPaths = folderPaths;
        this.validFiles = validFiles;
        this.config = config;
    }

    @Override
    public Long call() throws Exception {
        while (!folderPaths.isEmpty()) {
            File folderToPopulate = folderPaths.poll(10, TimeUnit.SECONDS);
            LinkedList<DTO> giftCertificateDTO = new LinkedList<>();
            giftCertificateDTO.addAll(factory.createValidJSONDTO(validFiles * 3));
            giftCertificateDTO.addAll(factory.createIncorrectFieldCertificateDTOS(3L));
            giftCertificateDTO.addAll(factory.createDBConstraintViolationCertificate(3L));
            giftCertificateDTO.addAll(factory.createNonValidBeanCertificate(3L));

        }
        return 1l;
    }


}
