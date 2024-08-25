package com.project.user.repositories;

import java.util.List;
import java.util.Optional;

import com.project.user.enums.Active;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public List<User> findAllByActive(Active activity);
	
	
	public Optional<User> findByEmail(String email);
	
}
