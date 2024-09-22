package com.mascotas.app.modules.shelters;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<ShelterEntity, Long>{
	public List<ShelterEntity> findAll();
}