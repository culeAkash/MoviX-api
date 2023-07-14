package com.project.user.auth;

import java.util.HashMap;
import java.util.Map;

import com.project.user.entities.User;

public class AuthStorage {

	public static Map<String, User> LOGGED_IN_USER_DATA = new HashMap<>();
	
	public static String USER = "loggedInUser";
	
	public static String isUserLoggedIn() {
		User loggedInUser = LOGGED_IN_USER_DATA.get(USER);
		System.out.println(loggedInUser);
		if(loggedInUser!= null) {
			if(loggedInUser.getRole()==1)
				return "admin";
			return "user";
		}
		return "noauth";
	}
	
	public static Boolean isUserLoggedIn(Long userId) {
		User user = AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER);
		if(user==null)
			return false;
		return userId==user.getUserId();
	}
}