package com.example.User_Service.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.User_Service.DTO.UserDTO;
import com.example.User_Service.Exception.UserNotFoundException;
import com.example.User_Service.Service.UserService;
import com.example.User_Service.model.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService service;

	@PostMapping("/save")
	public UserDTO save(@RequestBody UserDTO userdto) {
		return service.save(userdto);
	}
	
	@PostMapping("/update")
	public UserDTO update(@RequestBody UserDTO userdto) {
		return service.update(userdto);
	}

	@GetMapping("/find/{id}")
	public UserDTO FindById(@PathVariable("id") int id) throws UserNotFoundException {
		try {
			return service.FindById(id);
		} catch (Exception e) {
			throw new UserNotFoundException("User is not present");
		}
	}

	@GetMapping("/findbycity/{id}")
	public List<UserDTO> FindByCity(@PathVariable("id") String city) throws UserNotFoundException {
		try {
			return service.FindByCity(city);
		} catch (Exception e) {
			throw new UserNotFoundException("User is not present");
		}
	}

	@GetMapping("/findbyEmail/{id}")
	public UserDTO FindByEmail(@PathVariable("id") String email) {
		
			return service.FindByEmail(email);
		
	}

	public UserDTO deleteById(int id) throws UserNotFoundException {
		try {
			return service.deleteById(id);
		} catch (Exception e) {
			throw new UserNotFoundException("User is not present");
		}
	}

}
