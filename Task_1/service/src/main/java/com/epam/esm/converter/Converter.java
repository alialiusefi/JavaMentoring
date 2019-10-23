package com.epam.esm.converter;

import com.epam.esm.dto.DTO;
import com.epam.esm.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public interface Converter<D extends DTO, E extends Entity> {

    E toEntity(D dto);

    D toDTO(E entity);

    default List<E> toEntityList(List<D> dto) {
        List<E> entities = new ArrayList<>();
        for (D i : dto) {
            entities.add(toEntity(i));
        }
        return entities;
    }

    default List<D> toDTOList(List<E> entities) {
        List<D> giftCertificatesDTO = new ArrayList<>();
        for (E i : entities) {
            giftCertificatesDTO.add(toDTO(i));
        }
        return giftCertificatesDTO;
    }
}
