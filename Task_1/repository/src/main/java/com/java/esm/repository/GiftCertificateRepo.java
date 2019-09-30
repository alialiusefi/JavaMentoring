package com.java.esm.repository;

import com.java.esm.entity.GiftCertificate;
import com.java.esm.repository.specfication.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class GiftCertificateRepo extends CRUDRepo<GiftCertificate> {

    public GiftCertificateRepo (JdbcTemplate template, RowMapper<GiftCertificate> rowMapper) {
        super(template, rowMapper);
    }

    @Override
    public void add(GiftCertificate entity) {

    }

    @Override
    public void update(GiftCertificate entity) {

    }

    @Override
    public List<GiftCertificate> query(Specification specification) {
        return null;
    }

    @Override
    public void delete(GiftCertificate entity) {

    }

    @Override
    public void delete(Specification specification) {

    }
}
