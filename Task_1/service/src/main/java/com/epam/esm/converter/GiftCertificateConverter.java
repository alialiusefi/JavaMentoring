package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateConverter extends BaseConverter implements Converter<GiftCertificateDTO, GiftCertificate> {

    @Autowired
    protected GiftCertificateConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDTO giftCertificateDTO) {
        return modelMapper.map(giftCertificateDTO, GiftCertificate.class);

    }

    @Override
    public GiftCertificateDTO toDTO(GiftCertificate entity) {
        return modelMapper.map(entity, GiftCertificateDTO.class);
    }


    @Override
    public List<GiftCertificate> toEntityList(List<GiftCertificateDTO> dto) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        for (GiftCertificateDTO i : dto) {
            giftCertificates.add(toEntity(i));
        }
        return giftCertificates;
    }

    @Override
    public List<GiftCertificateDTO> toDTOList(List<GiftCertificate> entities) {
        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();
        for (GiftCertificate i : entities) {
            giftCertificatesDTO.add(toDTO(i));
        }
        return giftCertificatesDTO;
    }
}
