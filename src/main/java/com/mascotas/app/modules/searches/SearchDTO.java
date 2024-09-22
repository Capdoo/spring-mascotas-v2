package com.mascotas.app.modules.searches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SearchDTO {
	private Long id;
	private String address;
	private String district;
	private String phoneA;
	private String phoneB;
	//Pet
	private Long petId;
	private String namePet;
	private String speciesPet;
	private String breedPet;
	private String colour;
	//Fecha de usuario
	private String lostDate;
	private String registerDate;
	//Mensaje
	private String message;
	//MainPicture
	private String encoded;
	//Estado
	private Integer state;
}