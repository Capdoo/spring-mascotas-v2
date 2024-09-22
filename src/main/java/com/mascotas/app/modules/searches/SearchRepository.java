package com.mascotas.app.modules.searches;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mascotas.app.modules.pets.PetEntity;

public interface SearchRepository extends JpaRepository<SearchEntity, Long>{
	
	public List<SearchEntity> findAll();
	public Optional<SearchEntity> findByPet(PetEntity petEntity);
	public Boolean existsByPet(PetEntity petEntity);
	//public List<SearchEntity> findAllByOwner(OwnerEntity ownerEntity);
}
