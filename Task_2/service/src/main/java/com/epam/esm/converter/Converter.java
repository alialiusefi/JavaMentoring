package com.epam.esm.converter;

import com.epam.esm.dto.DTO;
import com.epam.esm.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public interface Converter<D extends DTO, E extends AbstractEntity> {

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
        List<D> dtos = new ArrayList<>();
        for (E i : entities) {
            dtos.add(toDTO(i));
        }
        return dtos;
    }

}
