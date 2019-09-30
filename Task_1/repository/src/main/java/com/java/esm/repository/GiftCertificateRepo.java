package com.java.esm.repository;

import com.java.esm.entity.GiftCertificate;
import com.java.esm.entity.Tag;
import com.java.esm.repository.specfication.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class GiftCertificateRepo extends CRUDRepo<GiftCertificate> {

    private static final String SQL_INSERT = "insert into giftcertificates " +
            "(id,name,description,price,date_created,date_modified,duration_till_expiry) values " +
            "(?,?,?,?,?,?,?)";
    private static final String SQL_DELETE = "delete from giftcertificates " +
            "where giftcertificates.id = ?";

    private static final String SQL_UPDATE = "update giftcertificates " +
            "set name = ? ,description = ? ,price = ?, " +
            "date_created = ? , date_modified = ? , " +
            "duration_till_expiry = ? where id = ?";

    public GiftCertificateRepo (JdbcTemplate template, RowMapper<GiftCertificate> rowMapper) {
        super(template, rowMapper);
    }


    @Override
    public void add(GiftCertificate entity) {
        jdbcTemplate.update(SQL_INSERT,getFieldsArray(entity));
    }

    @Override
    public void update(GiftCertificate entity) {
        jdbcTemplate.update(SQL_UPDATE,getFieldsArray(entity),entity.getId());
    }

    @Override
    public List<GiftCertificate> query(Specification specification) {
        return jdbcTemplate.query(specification.toSqlClauses(),
                rowMapper,specification.getParameters());
    }

    @Override
    public void delete(GiftCertificate entity) {
        jdbcTemplate.update(SQL_DELETE, entity.getId());
    }

    @Override
    public void delete(Specification specification) {
        List<GiftCertificate> certificatesToDelete = query(specification);
        for(GiftCertificate certificate : certificatesToDelete){
            jdbcTemplate.update(SQL_DELETE,certificate.getId());
        }
    }

    @Override
    protected Object[] getFieldsArray(GiftCertificate entity) {
        return new Object[]{
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getDateOfCreation(),
                entity.getDateOfModification(),
                entity.getDurationTillExpiry()
        };
    }

}
