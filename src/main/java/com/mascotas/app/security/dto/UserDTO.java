package com.mascotas.app.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String dni;
	private String email;
	private String phone1;
	private String phone2;
	private String username;
	//Para imagen
	private String encoded;
	private Integer state;
}
