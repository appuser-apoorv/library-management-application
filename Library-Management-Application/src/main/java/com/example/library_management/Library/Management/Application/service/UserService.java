package com.example.library_management.Library.Management.Application.service;

import com.example.library_management.Library.Management.Application.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<UserDTO> getAllUsers(String name , Pageable pageable);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser( Long id, UserDTO userDTO );

    void deleteUser(Long id);


}
