package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.specification.FindGiftCertificateByID;
import com.epam.esm.repository.specification.FindTagByName;
import com.epam.esm.repository.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


@Repository
public class GiftCertificateRepositoryImpl extends BaseCRUDRepository<GiftCertificate>
        implements GiftCertificateRepository {

 /*
      private static final String SQL_DELETE = "delete from giftcertificates " +
            "where giftcertificates.id = ?";

    private static final String SQL_UPDATE = "update giftcertificates " +
            "set name = ? ,description = ? ,price = ?, " +
            "date_created = ? , date_modified = ? , " +
            "duration_till_expiry = ? where id = ?";

    private static final String INSERT_REFERENCE_SQL = "insert into tagged_giftcertificates " +
            "(tag_id, gift_certificate_id) VALUES (?,?)";
    private static final String DELETE_REFERENCES_SQL = "delete from tagged_giftcertificates " +
            "where gift_certificate_id = ?";

    private static final String SQL_SELECT_BY_NAME = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates where giftcertificates.name = ? ";
*/

    @Autowired
    public GiftCertificateRepositoryImpl(/*JdbcTemplate template, GiftCertificateRowMapper rowMapper,*/
            EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    public GiftCertificate add(GiftCertificate entity) {
        entityManager.persist(entity);
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public void update(GiftCertificate entity) {
        /*Object[] fieldsArray = getFieldsArray(entity);
        Object[] fieldsArrayWithParameters = Arrays.copyOf(fieldsArray, fieldsArray.length + 1);
        fieldsArrayWithParameters[fieldsArrayWithParameters.length - 1] = entity.getId();
        jdbcTemplate.update(SQL_UPDATE, fieldsArrayWithParameters);*/
    }

    @Override
    public void delete(GiftCertificate entity) {
       /* jdbcTemplate.update(SQL_DELETE, entity.getId());*/
    }

    @Override
    public void delete(Specification specification) {
      /*  List<GiftCertificate> certificatesToDelete = queryList(specification);
        for (GiftCertificate certificate : certificatesToDelete) {
            jdbcTemplate.update(SQL_DELETE, certificate.getId());
        }*/
    }

/*
    public void addReference(GiftCertificate giftCertificate, Tag tag) {
        jdbcTemplate.update(INSERT_REFERENCE_SQL, tag.getId(), giftCertificate.getId());
    }

    public void deleteAllReferences(GiftCertificate certificate) {
        jdbcTemplate.update(DELETE_REFERENCES_SQL, certificate.getId());
    }
*/
}
