package com.epam.esm.service;

import com.epam.esm.dto.DTO;

import java.util.List;
import java.util.Map;

public interface BaseService<T extends DTO> {

    T getByID(long id);

    List<T> getAll(int pageNumber, int size);

    T add(T dto);

    boolean delete(T dto);

    boolean delete(long id);

    T update(T dto);

    T patch(Map<Object, Object> fields, long id);
}
