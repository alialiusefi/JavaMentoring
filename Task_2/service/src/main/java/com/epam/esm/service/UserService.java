package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;

import java.util.Map;

public interface UserService extends BaseService<UserDTO> {
    default UserDTO patch(Map<Object, Object> fields, Long id) {
        throw new UnsupportedOperationException("Unimplemented method!");
    }
}
