package com.epam.esm.service;

import com.epam.esm.dto.DTO;

public interface BaseService<T extends DTO> {

    T getByID(long id);

    T add(T dto);

    boolean delete(T dto);

    boolean delete(long id);

    T update(T dto);

}
