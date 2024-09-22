package com.mascotas.app.modules.breed;

import com.mascotas.app.modules.species.SpeciesEntity;
import com.mascotas.app.modules.species.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BreedResource {
    @Autowired
    BreedService breedService;
    @Autowired
    SpeciesService speciesService;

    public ResponseEntity<Object> readBySpecies(Long id) {
        List<BreedEntity> listBreedEntity;
        List<BreedDto> listSend;

        SpeciesEntity speciesEntity = speciesService.findById(id);
        if (speciesEntity == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        listBreedEntity = breedService.listAllBySpecies(speciesEntity);
        listSend = listBreedEntity.stream()
                .map(BreedMapper::mapBreedDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listSend);
    }


}
