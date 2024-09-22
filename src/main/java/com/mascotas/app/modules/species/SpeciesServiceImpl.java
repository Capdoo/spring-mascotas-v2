package com.mascotas.app.modules.species;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpeciesServiceImpl implements SpeciesService{

    @Autowired
    SpeciesRepository speciesRepository;

    @Override
    public List<SpeciesEntity> listAll() {
        return speciesRepository.findAll();
    }

    @Override
    public SpeciesEntity findById(Long id) {
        return speciesRepository.findById(id).orElse(null);
    }
}
