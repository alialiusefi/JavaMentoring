package com.epam.esm.repository.rowmapper;

import com.epam.esm.builder.GiftCertificateBuilder;
import com.epam.esm.builder.GiftCertificateBuilderImpl;
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
        GiftCertificateBuilder giftCertificateBuilder = new GiftCertificateBuilderImpl();
        giftCertificateBuilder.setID(resultSet.getInt("id"));
        giftCertificateBuilder.setText(resultSet.getString("name"),
                resultSet.getString("description"));
        giftCertificateBuilder.setPrice(BigDecimal.valueOf(resultSet.getDouble("price")));
        giftCertificateBuilder.setDates(
                convertToLocalDateViaSqlDate(resultSet.getDate("date_created")),
                convertToLocalDateViaSqlDate(resultSet.getDate("date_modified"))
        );
        giftCertificateBuilder.setDuration(resultSet.getInt("duration_till_expiry"));
        return giftCertificateBuilder.getResult();
    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
}
