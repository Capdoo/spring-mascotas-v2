package com.mascotas.app.modules.pets;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetResource {

    @Autowired
    PetService petService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserService userService;
    @Autowired
    FileUploadService fileUploadService;

    public ResponseEntity<Object> createPet(PetDto petDTO, String token) {
        String realToken;
        String username;
        PetEntity petCreate;

        realToken = token.split(" ")[1];
        username = jwtProvider.getNombreUsuarioFromToken(realToken);
        if (!userService.existsByUsername(username)){
            return ResponseEntity.notFound().build();
        }
        petCreate = petService.createPet(petDTO, username);
        petDTO = PetMapper.mapPetDto(petCreate);
        petDTO.setEncoded(fileUploadService.convertBytesToEncoded(petCreate.getImage()));
        return ResponseEntity.status(HttpStatus.CREATED).body(petDTO);
    }

    public ResponseEntity<Object> read() {
        List<PetEntity> listPets;
        List<PetDto> sendList;

        listPets = petService.listAllPets();
        sendList = listPets.stream().map(
                PetMapper::mapPetDto
        ).collect(Collectors.toList());

        return ResponseEntity.ok(sendList);
    }

    public ResponseEntity<Object> readPet(Long id) {
        PetEntity petRead;

        if (!petService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }
        petRead = petService.readPet(id);
        return ResponseEntity.status(HttpStatus.OK).body(PetMapper.mapPetDto(petRead));
    }

    public ResponseEntity<Object> readPetsByOwner(String token) throws ParseException {
        List<PetEntity> listPets;
        List<PetDto> listPetsSend;
        Long userId;

        String username = jwtProvider.getNombreUsuarioFromToken(token.split(" ")[1]);
        if(userService.readByUsername(username) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

        userId = jwtProvider.getUserIdFromToken(token.split(" ")[1]);
        UserEntity userEntity = userService.readUser(userId);

        listPets = petService.readPetByOwner(userEntity);

        listPetsSend = listPets.stream()
                .filter(e -> e.getState()==1)
                .map(PetMapper::mapPetDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(listPetsSend);
    }

    public ResponseEntity<Object> updatePet(Long id, PetDto petDto, String token) {
        UserEntity user;
        PetEntity petUpdate;

        if (!petService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }

        petDto.setId(id);
        petUpdate = petService.updatePet(petDto);
        return ResponseEntity.status(HttpStatus.OK).body(PetMapper.mapPetDto(petUpdate));
    }

    public ResponseEntity<Object> deletePet(Long id) {
        PetDto petDTO;
        PetEntity petDelete;

        if (!petService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }
        petDTO = new PetDto();
        petDTO.setId(id);
        petDelete = petService.deletePet(petDTO);
        return ResponseEntity.status(HttpStatus.OK).body(PetMapper.mapPetDto(petDelete));
    }
}