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
        return new GiftCertificate(
                resultSet.getInt("id"),
                resultSet.getNString("name"),
                resultSet.getNString("description"),
                BigDecimal.valueOf(resultSet.getDouble("price")),
                convertToLocalDateViaSqlDate(resultSet.getDate("date_created")),
                convertToLocalDateViaSqlDate(resultSet.getDate("date_modified")),
                resultSet.getInt("duration_till_expiry"));
    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
}
