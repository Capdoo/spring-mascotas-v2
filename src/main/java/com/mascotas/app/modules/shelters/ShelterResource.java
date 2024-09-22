package com.mascotas.app.modules.shelters;

import com.mascotas.app.modules.adoptions.AdoptionDto;
import com.mascotas.app.modules.adoptions.AdoptionEntity;
import com.mascotas.app.modules.adoptions.AdoptionMapper;
import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.security.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ShelterResource {

    @Autowired
    ShelterService shelterService;

    public ResponseEntity<Object> createShelter(@RequestBody ShelterDto shelterDto){
        UserEntity userEntity;
        ShelterEntity shelterEntity;

        userEntity = new UserEntity();
        shelterEntity = shelterService.createShelter(shelterDto, userEntity);
        shelterDto = ShelterMapper.mapShelterDto(shelterEntity);

        return new ResponseEntity<Object>(shelterDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> readShelters(){
        List<ShelterEntity> listSheltersEntities;
        List<ShelterDto> listSheltersDto;

        listSheltersEntities = shelterService.listAllShelters();
        listSheltersDto = listSheltersEntities.stream()
                .map(ShelterMapper::mapShelterDto)
                .collect(Collectors.toList());

        return new ResponseEntity<Object>(listSheltersDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> readShelter(Long id) {
        ShelterEntity shelterEntity;
        ShelterDto shelterDto;

        shelterEntity = shelterService.readShelter(id);
        shelterDto = ShelterMapper.mapShelterDto(shelterEntity);

        return new ResponseEntity<Object>(shelterDto, HttpStatus.OK);
    }

    public ResponseEntity<Object> readSheltersByPetId(Long petId) {
        List<ShelterEntity> listShelters;

        listShelters = shelterService.listAllShelters();
        listShelters = listShelters.stream()
                .filter(e -> e.getPets().stream()
                        .anyMatch(p -> p.getId() == petId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listShelters);
    }

    public ResponseEntity<Object> updateShelter(Long id, ShelterDto shelterDto) {
        ShelterEntity shelterUpdate;

        if (!shelterService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelter Not Found");
        }

        shelterDto.setId(id);
        shelterUpdate = shelterService.updateShelter(shelterDto);
        return ResponseEntity.ok(ShelterMapper.mapShelterDto(shelterUpdate));
    }

    public ResponseEntity<Object> deleteShelter(Long id) {
        ShelterDto shelterDto;
        ShelterEntity shelterDelete;

        if (!shelterService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelter Not Found");
        }
        shelterDto = new ShelterDto();
        shelterDto.setId(id);

        shelterDelete = shelterService.deleteShelter(shelterDto);
        return ResponseEntity.status(HttpStatus.OK).body(ShelterMapper.mapShelterDto(shelterDelete));
    }

}










