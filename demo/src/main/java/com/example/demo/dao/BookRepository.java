package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

	@SuppressWarnings("unchecked")
	Book save(Book book);
	
	Book deleteById(int id);
	
	Book findByName(String name);
	
	List<Book> findByAuthor(String author);
	
}
