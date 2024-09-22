package com.mascotas.app.modules.species;

import com.mascotas.app.modules.pets.PetDto;
import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.pets.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpeciesResource {

    @Autowired
    SpeciesService speciesService;

    public ResponseEntity<Object> read() {
        List<SpeciesEntity> listSpeciesEntity;
        List<SpeciesDto> listSend;

        listSpeciesEntity = speciesService.listAll();
        listSend = listSpeciesEntity.stream().map(
                SpeciesMapper::mapSpeciesDto
        ).collect(Collectors.toList());

        return ResponseEntity.ok(listSend);
    }

}
