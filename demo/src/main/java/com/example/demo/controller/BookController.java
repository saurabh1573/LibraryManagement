package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	
		@Autowired
		BookService service;
	    
		@PostMapping("/save")
	    public Book save(@RequestBody Book book) throws Exception{
				try {
					return service.save(book);
				} catch (UserNotFoundException e) {
					// TODO Auto-generated catch block
				throw new UserNotFoundException(e.getMessage());
				}
				catch(Exception e) {
					throw new BooknotavailableException(e.getMessage());
				}
	    }
		
		@DeleteMapping("/delete/{id}")
		public Book delete(@PathVariable("id") int id) throws Exception{
			try{
				return service.deletebyId(id);
			}
	    	catch(Exception e) {
	    		throw new BooknotavailableException("Book is not available");
	    	}
		}
		
		@GetMapping("/find/{name}")
		public Book findByName(@PathVariable("name") String name)  throws Exception{
			try {
				return service.findByName(name);
			}
			catch(Exception e) {
				throw new BooknotavailableException("Book is not available");
			}
		}
		@GetMapping("/author/{author}")
		 public List<Book> findByAuthor(@PathVariable("author") String author) throws Exception{
			 try {
			 return service.findByAuthor(author);
			 }
			 catch(Exception e) {
				 throw new BooknotavailableException("Book is not available");
			 }
		 }
}
