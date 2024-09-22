package com.mascotas.app.modules.pets;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import com.mascotas.app.modules.adoptions.AdoptionEntity;
import com.mascotas.app.modules.breed.BreedEntity;
import com.mascotas.app.modules.searches.SearchEntity;
import com.mascotas.app.modules.shelters.ShelterEntity;
import com.mascotas.app.security.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name="ptts_pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String gender;

	private Timestamp birthDate;

	private Timestamp registerDate;

	private String colour;

	private String characteristic;

	private String size;

	@Lob
	@Column(length = 16777215)
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] image;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "PET_FK_USER"))
	private UserEntity user;

	//Shelters
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shelter_id",referencedColumnName = "id", foreignKey = @ForeignKey(name = "PET_FK_SHELTER"))
	private ShelterEntity shelter;

	@OneToOne(cascade =  CascadeType.ALL,mappedBy = "pet")
	private SearchEntity search;

	//For pets
	@OneToMany(cascade =  CascadeType.ALL, mappedBy="pet")
	private Set<AdoptionEntity> adoptions;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "breed_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "PET_FK_BREED"))
	private BreedEntity breed;

	private Long speciesId;

	private Integer state;
}