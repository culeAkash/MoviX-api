package com.project.user.controllers;

import com.project.user.entities.User;
import com.project.user.payloads.AuthUserDTO;
import com.project.user.payloads.UserDTO;
import com.project.user.requests.RegisterRequest;
import com.project.user.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/public")
public class PublicController {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<UserDTO> createNewUser(@Valid @RequestBody RegisterRequest request){
        User createNewUser = this.userService.createNewUser(request);
        UserDTO userDTO = modelMapper.map(createNewUser, UserDTO.class);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<AuthUserDTO> getUserByEmail(@PathVariable String email) {
        AuthUserDTO userByEmail = this.userService.getUserByEmail(email);
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }


    @GetMapping("/getUserByUserId/{userId}")
    public ResponseEntity<UserDTO> getUserByUserId(@PathVariable Long userId){
        UserDTO userById = this.userService.getUserById(userId);
        return ResponseEntity.ok().body(userById);
    }


}
