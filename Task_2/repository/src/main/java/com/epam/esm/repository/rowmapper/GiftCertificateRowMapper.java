package com.epam.esm.repository.rowmapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public final class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        GiftCertificate.GiftCertificateBuilder giftCertificateBuilder = new GiftCertificate.GiftCertificateBuilder(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                BigDecimal.valueOf(resultSet.getDouble("price")),
                resultSet.getInt("duration_till_expiry")
        ).setDateOfCreation(convertToLocalDateViaSqlDate(resultSet.getDate("date_created")))
                .setDateOfModification(convertToLocalDateViaSqlDate(resultSet.getDate("date_modified")));
        return giftCertificateBuilder.getResult();
    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        return new Date(dateToConvert.getTime()).toLocalDate();
    }
}
