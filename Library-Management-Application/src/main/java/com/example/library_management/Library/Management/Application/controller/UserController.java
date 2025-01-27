package com.example.library_management.Library.Management.Application.controller;

import com.example.library_management.Library.Management.Application.dto.PaginatedResponse;
import com.example.library_management.Library.Management.Application.dto.UserDTO;
import com.example.library_management.Library.Management.Application.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "APIs for managing user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve all user available in the library")
    public ResponseEntity<PaginatedResponse<UserDTO>> getAllUsers(@RequestParam(required = false) String name,
                                     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                     @RequestParam(defaultValue = "id,asc") String[] sort){
        String sortBy = sort.length > 0 ? sort[0] : "id";
        String sortDir = sort.length >1 ? sort[1] : "asc";
        Sort sorting = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting);

        Page<UserDTO> users = userService.getAllUsers(name , pageable);

        PaginatedResponse<UserDTO> response = new PaginatedResponse<>();
        response.setContent(users.getContent());
        response.setCurrentPage(users.getNumber());
        response.setTotalPages(users.getTotalPages());
        response.setTotalElements(users.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create  a user", description = "Add a user in the library ")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        return  ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates a user in the  library")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user ", description = "Deletes a user from the  library")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User Deleted Successfully. ");
    }

}
