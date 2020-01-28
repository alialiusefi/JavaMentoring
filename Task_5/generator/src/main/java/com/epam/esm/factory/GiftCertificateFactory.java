package com.epam.esm.factory;

import com.epam.esm.dto.GeneratedGiftCertificateDTO;
import com.epam.esm.dto.IncorrectFieldNameGiftCertificateDTO;
import com.epam.esm.dto.TagDTO;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GiftCertificateFactory {

    public static final int STRING_LENGTH = 10;

    public GeneratedGiftCertificateDTO createValidCertificate() {
        String name = generateString(STRING_LENGTH);
        String description = generateString(STRING_LENGTH);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.01, 100.00));
        Integer duration = ThreadLocalRandom.current().nextInt(1, 6);
        return new GeneratedGiftCertificateDTO(0, name, description, price, duration, Arrays.asList(getRandomTag(), getRandomTag()));
    }

    public IncorrectFieldNameGiftCertificateDTO createIncorrectFieldCertificate() {
        String name = generateString(STRING_LENGTH);
        String description = generateString(STRING_LENGTH);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.01, 100.00));
        Integer duration = ThreadLocalRandom.current().nextInt(1, 6);
        return new IncorrectFieldNameGiftCertificateDTO(0, name, description, price, duration, Arrays.asList(getRandomTag(), getRandomTag()));
    }

    public GeneratedGiftCertificateDTO createNonValidBeanCertificate() {
        String name = generateString(STRING_LENGTH);
        String description = generateString(STRING_LENGTH);
        BigDecimal price = null;
        Integer duration = ThreadLocalRandom.current().nextInt(1, 6);
        return new GeneratedGiftCertificateDTO(0, name, description, price, duration, Arrays.asList(getRandomTag(), getRandomTag()));
    }

    public GeneratedGiftCertificateDTO createDBConstraintViolationCertificate() {
        String name = generateString(STRING_LENGTH + 100000);
        String description = generateString(STRING_LENGTH);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.01, 100.00));
        Integer duration = ThreadLocalRandom.current().nextInt(1, 6);
        return new GeneratedGiftCertificateDTO(0, name, description, price, duration, Arrays.asList(getRandomTag(), getRandomTag()));
    }

    public List<GeneratedGiftCertificateDTO> createNonValidBeanCertificate(Long size) {
        List<GeneratedGiftCertificateDTO> dtos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dtos.add(createNonValidBeanCertificate());
        }
        return dtos;
    }

    public List<GeneratedGiftCertificateDTO> createDBConstraintViolationCertificate(Long size) {
        List<GeneratedGiftCertificateDTO> dtos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dtos.add(createDBConstraintViolationCertificate());
        }
        return dtos;
    }

    public List<GeneratedGiftCertificateDTO> createValidJSONDTO(Long size) {
        List<GeneratedGiftCertificateDTO> dtos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dtos.add(createValidCertificate());
        }
        return dtos;
    }

    public List<IncorrectFieldNameGiftCertificateDTO> createIncorrectFieldCertificateDTOS(Long size) {
        List<IncorrectFieldNameGiftCertificateDTO> dtos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dtos.add(createIncorrectFieldCertificate());
        }
        return dtos;
    }

    private String generateString(int size) {
        byte[] array = new byte[size];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private TagDTO getRandomTag() {
        return new TagDTO(0l, generateString(5));
    }


}
