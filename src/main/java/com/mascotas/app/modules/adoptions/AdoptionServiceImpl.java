package com.mascotas.app.modules.adoptions;

import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.pets.PetRepository;
import com.mascotas.app.modules.pets.PetService;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserService;
import com.mascotas.app.utils.FechaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdoptionServiceImpl implements AdoptionService{

    @Autowired
    PetRepository petRepository;
    @Autowired
    AdoptionRepository adoptionRepository;
    @Autowired
    PetService petService;

    public List<AdoptionEntity> listAllAdoptions(){
        return adoptionRepository.findAll();
    }

    @Override
    public AdoptionEntity createAdoption(AdoptionDto adoptionDto) {
        PetEntity selectedPet = petRepository.findById(adoptionDto.getPet_id()).get();
        AdoptionEntity newAdoption = new AdoptionEntity();
        newAdoption.setAddress(adoptionDto.getAddress());
        newAdoption.setDistrict(adoptionDto.getDistrict());
        newAdoption.setRegisterDate(new Timestamp(System.currentTimeMillis()));
        newAdoption.setPhoneA(adoptionDto.getPhoneA());
        newAdoption.setPhoneB(adoptionDto.getPhoneB());
        newAdoption.setMessage(adoptionDto.getMessage());
        newAdoption.setPet(selectedPet);
        newAdoption.setObservation(adoptionDto.getObservation());
        newAdoption.setState(1);

        return adoptionRepository.save(newAdoption);
    }

    @Override
    public AdoptionEntity readAdoption(Long id) {
        return adoptionRepository.findById(id).orElse(null);
    }

    @Override
    public AdoptionEntity updateAdoption(AdoptionDto adoptionDto) {
        AdoptionEntity adoptionUpdate;

        adoptionUpdate = this.readAdoption(adoptionDto.getId());
        if (adoptionUpdate == null) {
            return null;
        }

        adoptionUpdate.setAddress(adoptionDto.getAddress());
        adoptionUpdate.setDistrict(adoptionDto.getDistrict());
        adoptionUpdate.setPhoneA(adoptionDto.getPhoneA());
        adoptionUpdate.setPhoneB(adoptionDto.getPhoneB());
        adoptionUpdate.setMessage(adoptionDto.getMessage());
        adoptionUpdate.setObservation(adoptionDto.getObservation());

        return adoptionRepository.save(adoptionUpdate);
    }

    @Override
    public AdoptionEntity deleteAdoption(AdoptionDto adoptionDto) {
        AdoptionEntity adoptionEntity;
        adoptionEntity = readAdoption(adoptionDto.getId());
        adoptionEntity.setState(0);
        return adoptionRepository.save(adoptionEntity);
    }

    @Override
    public List<AdoptionEntity> listAllByPetId(Long petId) {
        PetEntity petEntity = petService.readPet(petId);
        if (petEntity == null) {
            return null;
        }
        return adoptionRepository.findAllByPet(petEntity);
    }

//    @Override
//    public List<AdoptionEntity> listAllByOwner(UserEntity userEntity){
//        List<PetEntity> listPets;
//        List<Set<AdoptionEntity>> listRawAdoptions;
//        List<AdoptionEntity> listAdoptions;
//
//        listPets = petRepository.findAllByUser(userEntity);
//        listRawAdoptions = listPets.stream()
//                .map(PetEntity::getAdoptions)
//                .filter(adoptions -> !adoptions.isEmpty())
//                .collect(Collectors.toList());
//        listAdoptions = listRawAdoptions.stream()
//                .flatMap(Set::stream)
//                .collect(Collectors.toList());
//        return listAdoptions;
//    }


    @Override
    public boolean existsById(Long id) {
        return adoptionRepository.existsById(id);
    }



    //Obtener todos




    //Obtener por mascota_id

    //Obtener por mascota_id
    public List<AdoptionEntity> listAllByPetId(long petId){
        PetEntity mascotaSeleccionada = petRepository.findById(petId).get();
        return adoptionRepository.findAllByPet(mascotaSeleccionada);
    }



}
