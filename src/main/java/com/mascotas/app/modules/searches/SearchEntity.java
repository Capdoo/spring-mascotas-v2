package com.mascotas.app.modules.searches;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.mascotas.app.modules.pets.PetEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ptts_searches")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SearchEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Address can not be empty")
	@Column(nullable = false, name = "address")
	private String address;

	@NotEmpty(message = "District can not be empty")
	@Column(nullable = false, name = "district")
	private String district;

	@Column(nullable = false, name = "register_date")
	private Timestamp registerDate;

	@Column(nullable = false, name = "lost_date")
	private Timestamp lostDate;

	@NotEmpty(message = "Phone can not be empty")
	@Column(nullable = false, name = "phone_a")
	private String phoneA;

	@Column(name = "phone_b")
	private String phoneB;

	@NotEmpty(message = "Message can not be empty")
	@Column(nullable = false, name = "message")
	private String message;
	/*
	@ManyToOne
	@JoinColumn(name="pet_id",referencedColumnName = "id", nullable=false)
	private PetEntity pet;
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "SEARCH_FK_PET"), unique = true)
	private PetEntity pet;

	private Integer state;
}