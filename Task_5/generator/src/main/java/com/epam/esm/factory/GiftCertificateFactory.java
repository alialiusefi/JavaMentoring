package com.epam.esm.factory;

import com.epam.esm.dto.GiftCertificateDTO;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GiftCertificateFactory {

    public static final int STRING_LENGTH = 10;

    public GiftCertificateDTO createValidCertificate() {
        String name = generateString(STRING_LENGTH);
        String description = generateString(STRING_LENGTH);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.01, 100.00));
        Integer duration = ThreadLocalRandom.current().nextInt(1, 6);


    }

    public GiftCertificateDTO createNonValidBeanCertificate() {
        return null;
    }

    public GiftCertificateDTO createDBConstraintViolationCertificate() {
        return null;
    }

    private String generateString(int size) {
        byte[] array = new byte[size];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

}
