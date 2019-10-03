package com.epam.esm.converter;

import com.epam.esm.dto.DTO;
import com.epam.esm.entity.Entity;

public interface IConvert<D extends DTO, E extends Entity> {

    E toEntity(D dto);

    D toDTO(E entity);

}
