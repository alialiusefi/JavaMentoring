package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate,Long> {

    List<GiftCertificate> findByName(String name);


}
