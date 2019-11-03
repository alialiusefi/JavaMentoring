package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Authority;

import java.util.List;

public interface UserService extends BaseService<UserDTO> {

    UserDTO getByUserName(String username);

    UserDTO signUp(UserDTO userDTO);

    UserDTO logIn(UserDTO userDTO);

    List<Authority> getAuthorityList(UserDTO userDetails);
}
