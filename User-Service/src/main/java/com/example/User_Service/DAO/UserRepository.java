package com.example.User_Service.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.User_Service.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@SuppressWarnings("unchecked")
	public User save(User user);
	
	public User findById(int id);
	
	public List<User> findAllByCity(String city);
	
	public User findByEmailid(String email);
	
	public User deleteById(int id);
	
	
}
