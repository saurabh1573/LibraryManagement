package com.example.AuthService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredDTO {
	
	private int id;
	private String name;
	private String email;
	private String password;
	private String authority;
}
