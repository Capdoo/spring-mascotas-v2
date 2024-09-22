package com.mascotas.app.modules.adoptions;

import com.mascotas.app.security.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdoptionService {
	List<AdoptionEntity> listAllAdoptions();
	//Crud
	AdoptionEntity createAdoption(AdoptionDto adoptionDto);
	AdoptionEntity readAdoption(Long id);
	AdoptionEntity updateAdoption(AdoptionDto adoptionDto);
	AdoptionEntity deleteAdoption(AdoptionDto adoptionDto);
	//Business
	List<AdoptionEntity> listAllByPetId(Long petId);
//	List<AdoptionEntity> listAllByOwner(UserEntity userEntity);
	boolean existsById(Long id);
}