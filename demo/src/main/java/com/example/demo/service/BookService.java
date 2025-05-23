package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.dao.BookRepository;
import com.example.demo.model.Book;

@Service
public class BookService {
	
	@Autowired
	BookRepository repository;
	
	@Autowired
	RestTemplate template;
	@Autowired
	BookDTOMapper mapper;
	
	@Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, UserDTO message) {
        kafkaTemplate.send(topic, message);
    }
    public Book save(Book book) throws Exception {
    	UserDTO user=null;
    	try {
    	 user= template.getForObject("http://USERSERVICE/user/findbyEmail/"+book.getUseremail(), UserDTO.class);
    	}
    	catch(Exception e) {
    		throw new UserNotFoundException("User is not present");
    	}
    	try {
    	 book=repository.save(book);
    	 if(user.getBooks()==null) {
    		 List<Integer> books=new ArrayList<>();
    		 books.add(book.getId());
    		 user.setBooks(books);
    	 }
    	 else {
    		 user.getBooks().add(book.getId());
    	 }
    	 try {
    		 sendMessage("book-events",user);
    	 //UserDTO response= template.postForObject("http://USERSERVICE/user/update",user,UserDTO.class);
    	 }
    	 catch(Exception e) {
    		 throw new UserNotFoundException("User is not present");
    	 }
    	}
    	catch(Exception e) {
    		throw new Exception(e.getMessage());
    	}
    	
    	return book;
    }
	
	public Book deletebyId(int id){
		Book book=repository.deleteById(id);
   	return book;
	}
	
	public Book findByName(String name)  {
		Book book=repository.findByName(name);
		
		return book;
	}
	
	 public List<Book> findByAuthor(String author) {
			List<Book> book=repository.findByAuthor(author);
			
			return book;
	 }
}
