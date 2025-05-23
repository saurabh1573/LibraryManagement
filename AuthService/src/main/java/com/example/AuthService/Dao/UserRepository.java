package com.example.AuthService.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AuthService.model.UserCredentials;

public interface UserRepository extends JpaRepository<UserCredentials, Integer> {
	
	 Optional<UserCredentials> findById(int id);
	
	public Optional<UserCredentials> findByName(String name);
	
	public Optional<UserCredentials> findByEmail(String email);
}
