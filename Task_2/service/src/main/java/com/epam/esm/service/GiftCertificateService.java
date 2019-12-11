package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.PageDTO;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {

    default List<GiftCertificateDTO> getGiftCertificates(Long[] tagID, String name, String desc, Integer sortByDate,
                                                         Integer sortByName, Integer pageNumber, Integer pageSize) {
        throw new UnsupportedOperationException("Incorrect Method invoked!");
    }

    PageDTO getGiftCertificatesPage(Long[] tagID, String name, String desc, Integer sortByDate,
                                                 Integer sortByName, Integer pageNumber, Integer pageSize);


    default List<GiftCertificateDTO> getAll(int pageNumber, int size) {
        throw new UnsupportedOperationException("Incorrect Method invoked!");
    }



    PageDTO getAllPage(int pageNumber, int pageSize);


}

