package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {

    List<GiftCertificateDTO> getGiftCertificates(Long[] tagID, String name, String desc, Integer sortByDate,
                                                 Integer sortByName, Integer pageNumber, Integer pageSize);


}

