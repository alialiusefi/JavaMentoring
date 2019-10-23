package com.epam.esm.builder;

import com.epam.esm.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GiftCertificateBuilderImpl extends EntityBuilderImpl implements GiftCertificateBuilder {

    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate dateOfCreation;
    private LocalDate dateOfModification;
    private Integer durationTillExpiry;

    @Override
    public void setText(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void setDates(LocalDate dateOfCreation, LocalDate dateOfModification) {
        this.dateOfCreation = dateOfCreation;
        this.dateOfModification = dateOfModification;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public void setDuration(Integer duration) {
        this.durationTillExpiry = duration;
    }

    public GiftCertificate getResult() {
        return new GiftCertificate(
                id,
                name,
                description,
                price,
                dateOfCreation,
                dateOfModification,
                durationTillExpiry
        );
    }

}
