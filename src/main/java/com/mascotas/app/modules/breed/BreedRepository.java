package com.mascotas.app.modules.breed;

import com.mascotas.app.modules.species.SpeciesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<BreedEntity, Long> {
    List<BreedEntity> findAllBySpecies(SpeciesEntity speciesEntity);
}