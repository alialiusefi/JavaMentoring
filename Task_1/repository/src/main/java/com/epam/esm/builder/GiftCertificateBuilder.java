package com.epam.esm.builder;

import com.epam.esm.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface GiftCertificateBuilder extends EntityBuilder {

    void setText(String name, String description);

    void setDates(LocalDate dateOfCreation,LocalDate dateOfModification);

    void setPrice(BigDecimal price);

    void setDuration(Integer duration);

    GiftCertificate getResult();
}
