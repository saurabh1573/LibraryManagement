package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.UserDTO;
import com.example.demo.model.User;
import com.example.demo.DTO.BookDTO;
import com.example.demo.model.Book;

@Component
public class BookDTOMapper {
	
	
	public Book dtotomodel(BookDTO dto) {
		Book model=new Book();
		model.setName(dto.getName());
		model.setAuthor(dto.getAuthor());
		model.setLendingprice(dto.getLendingprice());
		//model.setUser(dto.getUser());
		model.setUseremail(dto.getUseremail());
		model.setLendingperiod(dto.getLendingperiod());
		return model;
	}
	
	public BookDTO modeltodto(Book model) {
		BookDTO dto=new BookDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setAuthor(model.getAuthor());
		dto.setBorrowdate(model.getBorrowdate());
		dto.setUseremail(model.getUseremail());
		//dto.setUser(model.getUser());
		dto.setAvailableon(model.getBorrowdate().plusDays(model.getLendingperiod()));
		return dto;
	}
	public UserDTO usermodeltodto(User model) {
		UserDTO dto=new UserDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setEmailid(model.getEmailid());
		dto.setCity(model.getCity());
		dto.setBooks(model.getBooks());
		return dto;
	}
}
