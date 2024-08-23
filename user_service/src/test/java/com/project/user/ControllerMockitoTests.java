package com.project.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.user.controllers.UserController;
import com.project.user.entities.User;
import com.project.user.services.FileService;
import com.project.user.services.impl.UserServiceImpl;

@WebMvcTest({UserController.class})
public class ControllerMockitoTests {
	
	@MockBean
	UserServiceImpl userService;
	
	@MockBean
	FileService fileService;
	
	@Autowired
	MockMvc mockMvc;
	
	
	@Test
	@Order(1)
	public void test_createNewUser() throws Exception {
		User user = new User(1l, "Akash Jaiswal", "akash@gmail.com", "Akash@123", 0, "Hello I am Akash Jaiswal", null);
		
		
		when(userService.createNewUser(user)).thenReturn(user);
		
		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user))).andExpect(status().isCreated());
	}
	
	
	@Test
	@Order(2)
	public void test_updateUser() throws Exception{
		User user = new User(1l, "Akash Jaiswal", "akash@gmail.com", "Akash@123", 0, "Hello I am Akash Jaiswal", null);
		
		when(userService.updateUser(new User(null,"Akash","akash@gmail.com","Akashjais@929",0,"", null),1l)).thenReturn(new User(1l,"Akash","akash@gmail.com","Akashjais@929",0,"", null));
		
		mockMvc.perform(put("/users/{userId}", 1l).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(status().isAccepted());
	}
	
	
	@Test
	@Order(3)
	public void test_deleteUser() throws Exception{
		when(userService.deleteUser(1L)).thenReturn(new User());
		
		mockMvc.perform(delete("/users/{userId}",1L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.message").value("User has been deleted successfully,Please Login"))
		.andExpect(jsonPath("$.isSuccessful").value(true));
	}
	
	
	@Test
	@Order(4)
	public void test_getAllUsers() throws Exception{
		
		List<User> users = new ArrayList<>();
		
		users.add(new User(1L, "Akash", null, null, null, null, null));
		users.add(new User(2L, "Aman", null, null, null, null, null));
		users.add(new User(3L, "Arinha", null, null, null, null, null));
		
		
		
		when(userService.getAllUsers()).thenReturn(users);
		
		mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].userId").value(1L))
		.andExpect(jsonPath("$[1].name").value("Aman"));
	}
	
	
	@Test
	@Order(4)
	public void test_getUserById() throws Exception{
		when(userService.getUserById(1L)).thenReturn(new User(1L, "Akash", null, null, null, null, null));
		
		mockMvc.perform(get("/users/{userId}", 1L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.userId").value(1L));
	}
	
	
	@Test
	@Order(5)
	public void test_getuserByUserName() throws Exception{
when(userService.getUsersByUserName("Akash")).thenReturn(List.of(new User(1L, "Akash", null, null, null, null, null)));
		
		mockMvc.perform(get("/users/userByName/{userName}", "Akash").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Akash"));
	}
	
	
	@Test
	@Order(6)
	public void test_postImage() throws Exception {
		 User user = new User(1L, "Akash", "akash@gmail.com", null, null, null, null);
		 
		 when(userService.getUserById(1L)).thenReturn(user);
		 MockMultipartFile imageFile = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image data".getBytes());
		 
		 when(fileService.uploadImage("", imageFile, 1L)).thenReturn("d2b79c43-187a-451e-99b3-3b404fa3cfc8.png");
		 
		 user.setProfileImage("d2b79c43-187a-451e-99b3-3b404fa3cfc8.png");
		 when(userService.updateUser(user, 1L)).thenReturn(user);
		 
		 mockMvc.perform(multipart("/users/image/upload/{userId}", user.getUserId())
	                .file(imageFile)).andExpect(status().isCreated()).andExpect(jsonPath("$.content").isNotEmpty());
	}
	
	@Test
	@Order(7)
	public void test_changePassword() throws Exception {
		when(userService.changePassword(1L, new PasswordDTO("akash@gmail.com", anyString(), anyString()))).thenReturn(new User());
		
		mockMvc.perform(post("/users/changePassword/{userId}",1L).contentType(MediaType.APPLICATION_JSON)
				.content(new  ObjectMapper().writeValueAsString(new PasswordDTO()))).andExpect(status().isAccepted())
		.andExpect(jsonPath("$.message").value("Password is changed successfully")).andExpect(jsonPath("$.isSuccessful").value(true));
	}
	
	

}
