package com.mascotas.app.modules.breed;

import com.mascotas.app.modules.species.SpeciesEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BreedServiceImpl implements BreedService{
    @Autowired
    BreedRepository breedRepository;

    @Override
    public List<BreedEntity> listAll() {
        return breedRepository.findAll();
    }

    @Override
    public List<BreedEntity> listAllBySpecies(SpeciesEntity speciesEntity) {
        List<BreedEntity> listBreed;
        listBreed = breedRepository.findAllBySpecies(speciesEntity);
        return listBreed;
    }

    @Override
    public BreedEntity findById(Long id) {
        return breedRepository.findById(id).orElse(null);
    }
}