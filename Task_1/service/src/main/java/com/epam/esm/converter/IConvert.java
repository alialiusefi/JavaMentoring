package com.epam.esm.converter;

import com.epam.esm.dto.DTO;
import com.epam.esm.entity.Entity;

import java.util.List;

public interface IConvert<D extends DTO, E extends Entity> {

    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntityList(List<D> dto);

    List<D> toDTOList(List<E> entities);
}
