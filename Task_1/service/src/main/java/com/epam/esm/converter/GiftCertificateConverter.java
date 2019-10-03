package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateConverter extends BaseConverter implements IConvert<GiftCertificateDTO, GiftCertificate> {

    @Override
    public GiftCertificate toEntity(GiftCertificateDTO giftCertificateDTO) {
        return modelMapper.map(giftCertificateDTO, GiftCertificate.class);

    }

    @Override
    public GiftCertificateDTO toDTO(GiftCertificate entity) {
        return modelMapper.map(entity, GiftCertificateDTO.class);
    }
}
