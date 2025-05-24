package com.example.User_Service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.User_Service.DAO.UserRepository;
import com.example.User_Service.DTO.UserDTO;
import com.example.User_Service.model.User;

@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	@KafkaListener(topics = "book-events", groupId = "myGroup")
    public void listen(UserDTO message) {
        System.out.println("Received message: " + message);
        update(message);
    }
	
    public UserDTO save(UserDTO userdto) {
    	User user=dtotomodel(userdto);
    	User user1=repository.save(user);
    	return modeltodto(user1);
    }
	
	public UserDTO FindById(int id) {
		User user=repository.findById(id);
		return modeltodto(user);
	}
	
	public List<UserDTO> FindByCity(String city){
		List<User> users=repository.findAllByCity(city);
		List<UserDTO> userdtos=users.stream().map(e->modeltodto(e)).collect(Collectors.toList());
		return userdtos;
	}
	
	public UserDTO FindByEmail(String email) {
		User user=repository.findByEmailid(email);
		return modeltodto(user);
	}
	
	public UserDTO deleteById(int id) {
		User user=repository.deleteById(id);
		return modeltodto(user);
	}
	
	public User dtotomodel(UserDTO dto) {
		User user=new User();
		user.setName(dto.getName());
		user.setCity(dto.getCity());
		user.setEmailid(dto.getEmailid());
		user.setBooks(dto.getBooks());
		return user;
	}
	
	public UserDTO modeltodto(User model) {
		UserDTO dto=new UserDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setEmailid(model.getEmailid());
		dto.setCity(model.getCity());
		dto.setBooks(model.getBooks());
		return dto;
	}

	public UserDTO update(UserDTO userdto) {
		// TODO Auto-generated method stub
		User existinguser=repository.findById(userdto.getId());
		existinguser.setBooks(userdto.getBooks());
		User updateduser=repository.save(existinguser);
		return modeltodto(updateduser);
	}
}
