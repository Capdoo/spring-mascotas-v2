package com.mascotas.app.modules.searches;

import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.pets.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchResource {
    @Autowired
    SearchService searchService;
    @Autowired
    PetService petService;

    public ResponseEntity<Object> createSearch(SearchDTO searchDTO) {
        SearchEntity searchCreate;

        if (!petService.existsById(searchDTO.getPetId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }

        searchCreate = searchService.createSearch(searchDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(searchCreate);
    }

    public ResponseEntity<Object> read(){
        List<SearchEntity> listDB = searchService.listAllSearchs();
        List<SearchDTO> listSearchs = listDB.stream().map(
                SearchMapper::mapSearchDto
        ).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listSearchs);
    }

    public ResponseEntity<Object> readSeach(Long id){
        SearchEntity searchEntity;
        searchEntity = searchService.readSearch(id);
        if (searchEntity == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(SearchMapper.mapSearchDto(searchEntity));
    }

    public ResponseEntity<Object> readByPetId(Long petId){
        SearchEntity searchEntity;
        PetEntity petEntity;

        if (!petService.existsById(petId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet Not Found");
        }
        petEntity = petService.readPet(petId);

        if (!searchService.existsByPet(petEntity)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet With No Search");
        }
        searchEntity = searchService.readSearchByPet(petEntity);
        return ResponseEntity.status(HttpStatus.OK).body(SearchMapper.mapSearchDto(searchEntity));
    }

    public ResponseEntity<Object> updateSearch(Long id, SearchDTO searchDTO){
        SearchEntity searchUpdate;
        PetEntity petEntity;

        petEntity = searchService.readSearch(id).getPet();

        if (petEntity == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet No Longer Exists");
        }

        searchDTO.setId(id);
        searchUpdate = searchService.updateSearch(searchDTO);
        return ResponseEntity.status(HttpStatus.OK).body(SearchMapper.mapSearchDto(searchUpdate));
    }

    public ResponseEntity<Object> deleteSearch(Long id){
        SearchEntity searchEntity;
        SearchDTO searchDTO;

        searchEntity = searchService.readSearch(id);
        if (searchEntity == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search Not Found");
        }

        searchDTO = new SearchDTO();
        searchDTO.setId(id);

        searchEntity = searchService.deleteSearch(searchDTO);

        return ResponseEntity.status(HttpStatus.OK).body(SearchMapper.mapSearchDto(searchEntity));
    }

}
