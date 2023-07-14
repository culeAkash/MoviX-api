package com.project.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.project.user.auth.AuthStorage;
import com.project.user.auth.LoginData;
import com.project.user.entities.User;
import com.project.user.exceptions.ForbiddenRequestException;
import com.project.user.exceptions.InvalidCredentialsException;
import com.project.user.exceptions.ResourceNotFoundException;
import com.project.user.payloads.PasswordDTO;
import com.project.user.payloads.ReviewDto;
import com.project.user.repositories.UserRepository;
import com.project.user.services.AuthService;
import com.project.user.services.impl.AuthServiceImpl;
import com.project.user.services.impl.UserServiceImpl;


@SpringBootTest(classes = {ServiceMockitoTests.class})
public class ServiceMockitoTests {

	
	@Mock
	UserRepository userRepository;
	
	@Mock
	ModelMapper mapper;
	
	@InjectMocks
	UserServiceImpl userService;
	
	@InjectMocks
	AuthServiceImpl authService;
	
	
	
	
	
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
	@Order(3)
	public void test_doLogin() throws Exception {
		User user = new User(2l, "Aman", "aman929@gmail.com","Amanjais@929", 1, "Hello", null);
		
		when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
		
		authService.doLogin(new LoginData(user.getEmail(), user.getPassword()));
		assertEquals(AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER).getEmail(),user.getEmail());
		authService.doLogOut();
	}
	
	@Test
	@Order(4)
	public void test_doLogout() {
		User user = new User(2l, "Aman", "aman929@gmail.com","Amanjais@929", 1, "Hello", null);
		AuthStorage.LOGGED_IN_USER_DATA.put(AuthStorage.USER, user);
		
		authService.doLogOut();
		
		assertThat(AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER)).isNull();
	}
	
	
	@Test
	@Order(5)
	public void test_createNewUser_emailAlreadyPresent_throwsException() {
		when(userRepository.findUserByEmail("akash@gmail.com")).thenReturn(new User(1L,"Akash","akash@gmail.com",null,null,null,null));
		
		InvalidCredentialsException invalidCredentialsException = assertThrows(InvalidCredentialsException.class, ()-> userService.createNewUser(new User(null, "Aman", "akash@gmail.com", null, null, null, null)));
		
		assertEquals("Email is already in use...Please try some other email", invalidCredentialsException.getMessage());
	}
	
	@Test
	@Order(6)
	public void test_updateUser_UserNotLoggedIn_throwsException() {
		ForbiddenRequestException forbiddenRequestException = assertThrows(ForbiddenRequestException.class, 
				()-> userService.updateUser(new User(), 1L));
		
		assertEquals("You don't have permission to access this URL", forbiddenRequestException.getMessage());
	}
	
	@Test
	@Order(7)
	public void test_updateUser_ThrowsUserNotFoundException() {
		User user = new User(2l, "Aman", "aman929@gmail.com","Amanjais@929", 1, "Hello", null);
		AuthStorage.LOGGED_IN_USER_DATA.put(AuthStorage.USER, user);
		
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		
		ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, 
				()-> userService.updateUser(new User(), 2L));
		
		assertEquals("User is not found for userId : 2", resourceNotFoundException.getMessage());
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
	public void test_createUser() throws InvalidCredentialsException{
		
		User user = new User(null, "Akash", "akash@gmail.com", "Akash@929", null, "Hello", null);
		
		when(userRepository.findUserByEmail("akash@gmail.com")).thenReturn(null);
		
		when(userRepository.save(user)).thenReturn(new User(1L, user.getName(), user.getEmail(), user.getPassword(), 0, user.getAbout(), null));
		
		User createNewUser = userService.createNewUser(user);
		
		assertEquals(createNewUser.getRole(), 0);
	}
	
	
	@Test
	@Order(11)
	public void test_changePassword() throws Exception{
		User user = new User(2l, "Aman", "aman929@gmail.com","Amanjais@929", 1, "Hello", null);
		AuthStorage.LOGGED_IN_USER_DATA.put(AuthStorage.USER, user);
		
		when(userRepository.findUserByEmail("aman929@gmail.com")).thenReturn(user);
		when(userRepository.save(user)).thenReturn(new User(2L,user.getName(),user.getEmail(),"Aman@123",user.getRole(),user.getAbout(),null));
		
		User changePassword = userService.changePassword(2L, new PasswordDTO("aman929@gmail.com", "Amanjais@929", "Aman@123"));
		
		assertEquals(changePassword.getPassword(), "Aman@123");
	}
	
	@Test
	@Order(12)
	public void test_changePassword_UserNotLoggedIn_throwsForbiddenRequestException(){
		ForbiddenRequestException forbiddenRequestException = assertThrows(ForbiddenRequestException.class, ()->
		userService.changePassword(1L, new PasswordDTO(null, null, null)));
		
		assertEquals("You don't have permission to access this URL", forbiddenRequestException.getMessage());
	}
	
	
	@Test
	@Order(13)
	public void test_changePassword_UserIsNotPresentWithGivenEmail_throwsException() {
		User user = new User(2l, "Aman", "aman929@gmail.com","Amanjais@929", 1, "Hello", null);
		AuthStorage.LOGGED_IN_USER_DATA.put(AuthStorage.USER, user);
		
		when(userRepository.findUserByEmail(new PasswordDTO("aman@gmail.com", null, null).getEmail())).thenReturn(null);
		
		InvalidCredentialsException invalidCredentialsException = assertThrows(InvalidCredentialsException.class, 
				()-> userService.changePassword(2L, new PasswordDTO("aman@gmail.com", null, null)));
		
		assertEquals("Email is not present or wrong!", invalidCredentialsException.getMessage());
	}
	
	@Test
	@Order(14)
	public void test_changePassword_oldPassNotMatchWithExistPass_throwsException() {
		User user = new User(2l, "Aman", "aman929@gmail.com","Amanjais@929", 1, "Hello", null);
		AuthStorage.LOGGED_IN_USER_DATA.put(AuthStorage.USER, user);
		
		when(userRepository.findUserByEmail(new PasswordDTO("aman929@gmail.com", "Aman@123", null).getEmail())).thenReturn(user);
		
		InvalidCredentialsException invalidCredentialsException = assertThrows(InvalidCredentialsException.class, 
				()-> userService.changePassword(2L, new PasswordDTO("aman929@gmail.com", "Aman@123", null)));
		
		assertEquals("Given password is wrong, can't change to new password", invalidCredentialsException.getMessage());
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
