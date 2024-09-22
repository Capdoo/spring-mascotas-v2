package com.mascotas.app.modules.species;

import java.util.List;

public interface SpeciesService {
    List<SpeciesEntity> listAll();
    SpeciesEntity findById(Long id);
}