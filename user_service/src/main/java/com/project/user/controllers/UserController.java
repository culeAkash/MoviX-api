package com.project.user.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.user.entities.User;
import com.project.user.payloads.ApiResponse;
import com.project.user.payloads.FileResponse;
import com.project.user.services.FileService;
import com.project.user.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;

	@Value("${project.image.users}")
	private String path;
	
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	

	
	//Get single user by id
	@GetMapping("/users/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Long userId){
		
		User userById = this.userService.getUserById(userId);
		logger.info("Requested user details {}",userById);
		return new ResponseEntity<User>(userById,HttpStatus.OK);
	}
	
	
	@GetMapping("/users/public")
	public ResponseEntity<List<User>> getAllUsers(){
				List<User> allUsers = this.userService.getAllUsers();
		return new ResponseEntity<>(allUsers,HttpStatus.OK);
	}
	
	
	//Controllers for Microservice communication
	
	@GetMapping("/services/users/getUser/{userId}")
	public User getUserByUserId(@PathVariable Long userId) {
		User user = this.userService.getUserService(userId);
		return user;
	}


	
	
	//Controllers to upload and get profile images
	@PostMapping("/users/image/upload/{userId}")
	public ResponseEntity<FileResponse<User>> postImage(@RequestParam("image") MultipartFile image,@PathVariable Long userId) throws IOException{
		User user = this.userService.getUserById(userId);
		
		String uploadImage = this.fileService.uploadImage(this.path , image,userId);// here we will handle exception using
	
		user.setProfileImage(uploadImage);

//		User updatedUser = this.userService.updateUser(null, userId);
		
		FileResponse<User> response = new FileResponse<>(null,true);
		return new ResponseEntity<FileResponse<User>>(response,HttpStatus.CREATED);
	}


	@GetMapping(value = "/users/image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE) // on firing this url in the
	// browser image will get
	// displayed
public void DownloadImage(@PathVariable Long userId, HttpServletResponse response) throws IOException{
		User user = this.userService.getUserById(userId);
		String imageUrl = user.getProfileImage();
		InputStream resource = this.fileService.getResource(this.path, imageUrl);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
}
	
 	
}
