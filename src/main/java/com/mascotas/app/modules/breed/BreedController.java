package com.mascotas.app.modules.breed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/breed")
public class BreedController {

    @Autowired
    BreedResource breedResource;

    @GetMapping("/read/species/{id}")
    public ResponseEntity<Object> readPets(@PathVariable(value = "id") Long id){
        return this.breedResource.readBySpecies(id);
    }

}