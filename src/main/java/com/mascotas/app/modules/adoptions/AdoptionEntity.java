package com.mascotas.app.modules.adoptions;

import java.sql.Timestamp;

import javax.persistence.*;

import com.mascotas.app.modules.pets.PetEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ptts_adoptions")
@Data
@AllArgsConstructor
public class AdoptionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String message;
	//In case the pet is sick
	private String observation;
	private String phoneA;
	private String phoneB;
	private String address;
	private String district;
	private Timestamp registerDate;

	private Integer state;
	
	@ManyToOne
	@JoinColumn(name="pet_id", referencedColumnName = "id", nullable=false, foreignKey = @ForeignKey(name = "ADOPTION_FK_PET"))
	private PetEntity pet;

	public AdoptionEntity() {
		super();
	}
}