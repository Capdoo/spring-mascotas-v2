package com.mascotas.app.modules.species;

public class SpeciesMapper {

    public static SpeciesDto mapSpeciesDto(SpeciesEntity speciesEntity) {
        return SpeciesDto.builder()
                .id(speciesEntity.getId())
                .description(speciesEntity.getDescription())
                .build();
    }
}
