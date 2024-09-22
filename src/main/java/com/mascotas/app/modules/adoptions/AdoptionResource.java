package com.mascotas.app.modules.adoptions;

import com.mascotas.app.modules.pets.PetDto;
import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.pets.PetMapper;
import com.mascotas.app.modules.pets.PetService;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AdoptionResource {

    @Autowired
    AdoptionService adoptionService;
    @Autowired
    UserService userService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    PetService petService;

    public ResponseEntity<Object> createAdoption(AdoptionDto adoptionDto) {
        AdoptionEntity adoptionEntity;
        adoptionEntity = adoptionService.createAdoption(adoptionDto);
        return ResponseEntity.ok(AdoptionMapper.mapAdoptionDto(adoptionEntity));

    }

    public ResponseEntity<Object> read() {
        List<AdoptionEntity> listAdoptions = adoptionService.listAllAdoptions();
        List<AdoptionDto> sendList = listAdoptions.stream().map(
                AdoptionMapper::mapAdoptionDto
        ).collect(Collectors.toList());
        return ResponseEntity.ok(sendList);
    }

    public ResponseEntity<Object> readAdoption(Long id) {
        if (!adoptionService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }
        AdoptionEntity adoptionEntity = adoptionService.readAdoption(id);
        return ResponseEntity.ok(adoptionEntity);
    }

    public ResponseEntity<Object> readAdoptionsByOwner(String token) throws ParseException {
        List<PetEntity> listPets;
        List<PetDto> listPetsSend;
        Long userId;
        UserEntity userEntity;

        String username = jwtProvider.getNombreUsuarioFromToken(token.split(" ")[1]);
        if(userService.readByUsername(username) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

        userId = jwtProvider.getUserIdFromToken(token.split(" ")[1]);
        userEntity = userService.readUser(userId);

        List<AdoptionEntity> listAllAdoptions = new ArrayList<>();
        listPets = petService.readPetByOwner(userEntity);
        for (PetEntity petEntity: listPets) {
            List<AdoptionEntity> listAdoptions = adoptionService.listAllByPetId(petEntity.getId()).stream()
                    .filter(Objects::nonNull)
                    .filter(e ->e.getState() == 1)
                    .collect(Collectors.toList());

            if (!listAdoptions.isEmpty()) {
                listAllAdoptions.addAll(listAdoptions);
            }
        }

        List<AdoptionDto> sendList = listAllAdoptions.stream().map(
                AdoptionMapper::mapAdoptionDto
        ).collect(Collectors.toList());
        return ResponseEntity.ok(sendList);

    }

    public ResponseEntity<Object> readAdoptionsByPetId(long petId) {
        List<AdoptionEntity> listAdoptions = adoptionService.listAllByPetId(petId);
        List<AdoptionDto> sendList = listAdoptions.stream().map(
                AdoptionMapper::mapAdoptionDto
        ).collect(Collectors.toList());
        return ResponseEntity.ok(sendList);
    }

    public ResponseEntity<Object> updateAdoption(Long id, AdoptionDto adoptionDto) {
        AdoptionEntity adoptionUpdate;

        if (!adoptionService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption Not Found");
        }

        adoptionDto.setId(id);
        adoptionUpdate = adoptionService.updateAdoption(adoptionDto);

        return ResponseEntity.status(HttpStatus.OK).body(AdoptionMapper.mapAdoptionDto(adoptionUpdate));
    }

    public ResponseEntity<Object> deleteAdoption(Long id) {
        AdoptionDto adoptionDto;
        AdoptionEntity adoptionDelete;

        if (!adoptionService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption Not Found");
        }
        adoptionDto = new AdoptionDto();
        adoptionDto.setId(id);
        adoptionDelete = adoptionService.deleteAdoption(adoptionDto);
        return ResponseEntity.status(HttpStatus.OK).body(AdoptionMapper.mapAdoptionDto(adoptionDelete));
    }
}