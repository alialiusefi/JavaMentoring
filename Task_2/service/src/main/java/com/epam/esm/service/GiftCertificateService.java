package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.PageDTO;

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {


    PageDTO getGiftCertificatesPage(String[] tagName, Long[] tagID, String name, String desc, Integer sortByDate,
                                    Integer sortByName, Integer pageNumber, Integer pageSize);





    PageDTO getAllPage(int pageNumber, int pageSize);


}

