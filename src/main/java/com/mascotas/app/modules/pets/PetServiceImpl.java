package com.mascotas.app.modules.pets;

import java.sql.Timestamp;
import java.util.List;

import com.mascotas.app.modules.breed.BreedEntity;
import com.mascotas.app.modules.breed.BreedRepository;
import com.mascotas.app.modules.breed.BreedService;
import com.mascotas.app.modules.species.SpeciesEntity;
import com.mascotas.app.modules.species.SpeciesService;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.utils.FechaUtil;

@Slf4j
@Service
public class PetServiceImpl implements PetService{
	@Autowired
	PetRepository petRepository;
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BreedService breedService;
	@Autowired
	SpeciesService speciesService;

	public List<PetEntity> listAllPets(){
		return petRepository.findAll();
	}

	@Override
	public PetEntity createPet(PetDto petDTO, String username) {
		PetEntity createPet;
		UserEntity userEntity;
		BreedEntity breedEntity;
		SpeciesEntity speciesEntity;
		String encoded;

		createPet = new PetEntity();
		createPet.setName(petDTO.getName());
		createPet.setGender(petDTO.getGender());
		createPet.setBirthDate(FechaUtil.getTimestampFromStringDate(petDTO.getBirthDate()));
		createPet.setRegisterDate(new Timestamp(System.currentTimeMillis()));
		createPet.setColour(petDTO.getColour());
		createPet.setCharacteristic(petDTO.getCharacteristic());
		createPet.setSize(petDTO.getSize());

		userEntity = userRepository.findByUsername(username).orElse(null);
		if (userEntity == null) {
			return null;
		}
		createPet.setUser(userEntity);
		createPet.setShelter(null);

		breedEntity = breedService.findById(petDTO.getBreedId());
		speciesEntity = breedEntity.getSpecies();

		createPet.setBreed(breedEntity);
		createPet.setSpeciesId(speciesEntity.getId());

		encoded = fileUploadService.obtenerEncoded(petDTO.getEncoded());
		byte[] image = fileUploadService.convertEncodedToBytes(encoded);
		createPet.setImage(image);
		createPet.setState(1);

		return petRepository.save(createPet);
	}

	@Override
	public PetEntity readPet(Long id) {
		return petRepository.findById(id).orElse(null);
	}

	@Override
	public PetEntity updatePet(PetDto petDTO) {
		PetEntity petDB = readPet(petDTO.getId());
		String encoded;

		assert petDB != null;
		petDB.setName(petDTO.getName());
		petDB.setGender(petDTO.getGender());
		petDB.setBirthDate(FechaUtil.getTimestampFromStringDate(petDTO.getBirthDate()));
		petDB.setColour(petDTO.getColour());
		petDB.setCharacteristic(petDTO.getCharacteristic());
		petDB.setSize(petDTO.getSize());

		encoded = fileUploadService.obtenerEncoded(petDTO.getEncoded());
		petDB.setImage(fileUploadService.convertEncodedToBytes(encoded));

		return petRepository.save(petDB);
	}

	@Override
	public PetEntity deletePet(PetDto petDTO) {
		PetEntity petDB;
		petDB = readPet(petDTO.getId());
		petDB.setState(0);
		return petRepository.save(petDB);
	}

	@Override
	public List<PetEntity> readPetByOwner(UserEntity userEntity) {
		return petRepository.findAllByUser(userEntity);
	}

	@Override
	public Boolean existsById(Long id) {
		return petRepository.existsById(id);
	}

}