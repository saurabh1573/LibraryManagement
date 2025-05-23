package com.example.demo.DTO;

import java.time.LocalDate;
import java.util.Date;

import com.example.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BookDTO {
	private int id;
	private String name;
	private String author;
	private String useremail;
	private int lendingprice;
	private LocalDate borrowdate;
	private LocalDate availableon;
	private int lendingperiod;
}
