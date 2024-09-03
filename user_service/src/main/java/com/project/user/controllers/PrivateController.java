package com.project.user.controllers;

import com.project.user.entities.User;
import com.project.user.payloads.UserDTO;
import com.project.user.services.UserService;
import feign.Response;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class PrivateController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> allActiveUsers = this.userService.getAllUsers();
        return ResponseEntity.ok().body(allActiveUsers);
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO){
        UserDTO updatedUser = userService.updateUser(userDTO,userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/uploadImage/{userId}")
    public ResponseEntity<>
}
