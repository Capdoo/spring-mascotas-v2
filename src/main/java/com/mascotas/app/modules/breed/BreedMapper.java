package com.mascotas.app.modules.breed;

public class BreedMapper {

    public static BreedDto mapBreedDto(BreedEntity breedEntity) {
        return BreedDto.builder()
                .id(breedEntity.getId())
                .description(breedEntity.getDescription())
                .build();
    }
}
