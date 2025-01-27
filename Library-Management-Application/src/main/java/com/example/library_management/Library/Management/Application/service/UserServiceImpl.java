package com.example.library_management.Library.Management.Application.service;

import com.example.library_management.Library.Management.Application.dto.UserDTO;
import com.example.library_management.Library.Management.Application.entity.User;
import com.example.library_management.Library.Management.Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserDTO> getAllUsers(String name , Pageable pageable) {
        return userRepository.findUserByName(name, pageable)
                .map(this::convertToDTO);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User Not Found"));
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setExpiryDate(userDTO.getExpiryDate());
        user.setPassword(userDTO.getPassword()); // If password is updated

        user = userRepository.save(user);

        return convertToDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setExpiryDate(user.getExpiryDate());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());


        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setExpiryDate(userDTO.getExpiryDate());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());


        return user;
    }
}
