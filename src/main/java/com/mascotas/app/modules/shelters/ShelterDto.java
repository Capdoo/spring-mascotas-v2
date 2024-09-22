package com.mascotas.app.modules.shelters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ShelterDto {
	private Long id;
	private String address;
	private String district;
	private String name;
	private long numberOfPartners;
	private String contactNumber;
	private String registerDate;
	private long idMainPartner;
	private String dniMainPartner;
	//For image
	private String encoded;
	private String urlLink;
}