package com.project.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
	@Query("select u from User u where u.name like %:name%")
	public List<User> findUserByUserName(@Param("name")String userName);
	
	
	@Query("select u from User u where u.email =:email")
	public User findUserByEmail(@Param("email")String email);
	
}
