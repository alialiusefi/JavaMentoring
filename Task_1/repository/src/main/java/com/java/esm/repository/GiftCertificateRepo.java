package com.java.esm.repository;

import com.java.esm.entity.GiftCertificate;
import com.java.esm.repository.specfication.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftCertificateRepo extends CRUDRepo<GiftCertificate> {

    protected GiftCertificateRepo(JdbcTemplate template, RowMapper<GiftCertificate> rowMapper) {
        super(template, rowMapper);
    }

    @Override
    public void add(GiftCertificate entity) {

    }

    @Override
    public void update(GiftCertificate entity) {

    }

    @Override
    public List query(Specification specification) {
        return null;
    }

    @Override
    public void delete(GiftCertificate entity) {

    }

    @Override
    public void delete(Specification specification) {

    }
}
