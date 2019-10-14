package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {

    List<GiftCertificateDTO> getGiftCertificate(long tagID, String name, String desc, int sortByDate, int sortByName);

    }

