package com.mascotas.app.modules.adoptions;

import com.mascotas.app.modules.pets.PetDto;
import lombok.*;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
public class AdoptionDto {
	private long id;
	private String message;
	//En caso el animal este enfermo
	private String observation;
	private String phoneA;
	private String phoneB;
	private String address;
	private String district;
	private String registerDate;
	private long pet_id;
	private PetDto petDto;

}
