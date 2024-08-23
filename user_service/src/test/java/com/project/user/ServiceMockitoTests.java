package com.project.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

//import com.project.user.auth.AuthStorage;
//import com.project.user.auth.LoginData;
import com.project.user.entities.User;
import com.project.user.exceptions.ResourceNotFoundException;
import com.project.user.payloads.ReviewDto;
import com.project.user.repositories.UserRepository;
import com.project.user.services.impl.UserServiceImpl;


@SpringBootTest(classes = {ServiceMockitoTests.class})
public class ServiceMockitoTests {

	
	@Mock
	UserRepository userRepository;
	
	@Mock
	ModelMapper mapper;
	
	@InjectMocks
	UserServiceImpl userService;
	
	
	
	
	
	@Test
	@Order(1)
	public void test_getAllUsers() {
		
		List<User> users = new ArrayList<>();
		
		users.add(new User(1l, "Akash", "akashjais929@gmail.com","Akashjais@929", 0, "Hello", null));
		users.add(new User(2l, "Aman", "aman929@gmail.com","Amanjais@929", 1, "Hello", null));
		
		
		
		when(userRepository.findAll()).thenReturn(users);
		
		assertEquals(2, userService.getAllUsers().size());
	}
	
	
	@Test
	@Order(2)
	public void test_getUserById() {
		User user = new User(2l, "Aman", "aman929@gmail.com","Amanjais@929", 1, "Hello", null);
		
		when(userRepository.findById(2l)).thenReturn(Optional.of(user));
		
		assertEquals("aman929@gmail.com", userService.getUserById(2l).getEmail());
	}
	

	

	
	

	


	
	@Test
	@Order(8)
	public void test_getUserById_UserWithIdNotPresent_throwsResourceNotFoundException() {
		
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		
		ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, 
				()-> userService.getUserById(2L));
		
		assertEquals("User is not found for userId : 2", resourceNotFoundException.getMessage());
	}
	
	
	@Test
	@Order(9)
	public void test_getUsersByUserName_userNotPresent_throwsResourceNotFoundException() {
		when(userRepository.findUserByUserName("Akash")).thenReturn(List.of());
		
		ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
				()-> userService.getUsersByUserName("Akash"));
		
		assertEquals("Users is not found for userName : Akash", resourceNotFoundException.getMessage());
	}
	
	
	@Test
	@Order(10)
	public void test_createUser(){
		
		User user = new User(null, "Akash", "akash@gmail.com", "Akash@929", null, "Hello", null);
		
		when(userRepository.findUserByEmail("akash@gmail.com")).thenReturn(null);
		
		when(userRepository.save(user)).thenReturn(new User(1L, user.getName(), user.getEmail(), user.getPassword(), 0, user.getAbout(), null));
		
		User createNewUser = userService.createNewUser(user);
		
		assertEquals(createNewUser.getRole(), 0);
	}
	
	

	

	
	@Test
	@Order(15)
	public void test_getDto() {
		when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(new ReviewDto(1L, null, null));
		
		ReviewDto dto = userService.getDto(any(Object.class));
		
		assertEquals(dto.getReviewId(), 1L);
	}
	
	@Test
	@Order(16)
	public void test_confirmUserPresence() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
		
		Boolean confirmUserService = userService.confirmUserService(1L);
		
		assertEquals(confirmUserService, true);
	}
	
	@Test
	@Order(17)
	public void test_getUserService() {
		User user = new User();
		user.setUserId(1L);
		user.setName("Akash");
		user.setEmail("akash@gmail.com");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		
		User user2 = userService.getUserService(1L);
		
		assertEquals(1L, user2.getUserId());
	}
	
}
