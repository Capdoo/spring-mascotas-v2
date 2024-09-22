package com.mascotas.app.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class NewUserDTO {
	private String username;
	private String dni;
	private String firstName;
	private String lastName;
	private String surName;
	private String phone1;
	private String phone2;
	private String province;
	private String district;
	private String address;
	private String email;
	private String password;
	//img
	private String encoded;
	private Set<String> roles = new HashSet<>();
}
