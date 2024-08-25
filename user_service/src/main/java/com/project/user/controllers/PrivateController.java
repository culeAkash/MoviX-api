package com.project.user.controllers;

import com.project.user.entities.User;
import com.project.user.payloads.UserDTO;
import com.project.user.services.UserService;
import feign.Response;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class PrivateController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @Valid UserDTO userDTO){
        UserDTO updatedUser = userService.updateUser(userDTO,userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
