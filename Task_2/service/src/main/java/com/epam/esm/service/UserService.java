package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;

public interface UserService extends BaseService<UserDTO> {

    UserDTO getByUserName(String username);

    UserDTO signUp(UserDTO userDTO);

    UserDTO logIn(UserDTO userDTO);

}
