package com.mascotas.app.modules.shelters;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.mascotas.app.modules.pets.PetEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ptts_shelters")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ShelterEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long numberOfPartners;
	private Date registerDate;
	private String contactNumber;
	private String address;
	private String district;
	//Image link
	private String linkImg;
	//Rel to set of partners
	//For pets
	@OneToMany(mappedBy="shelter")
	private Set<PetEntity> pets;

	private Integer state;
}