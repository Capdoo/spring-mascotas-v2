package com.mascotas.app.modules.adoptions;

import com.mascotas.app.modules.pets.PetMapper;
import com.mascotas.app.utils.FechaUtil;

public class AdoptionMapper {

    public static AdoptionDto mapAdoptionDto(AdoptionEntity adoptionEntity) {
        return AdoptionDto.builder()
                .id(adoptionEntity.getId())
                .address(adoptionEntity.getAddress())
                .district(adoptionEntity.getDistrict())
                .registerDate(FechaUtil.getStrindDateFromTimestamp(adoptionEntity.getRegisterDate()))
                .pet_id(adoptionEntity.getPet().getId())
                .phoneA(adoptionEntity.getPhoneA())
                .phoneB(adoptionEntity.getPhoneB())
                .message(adoptionEntity.getMessage())
                .observation(adoptionEntity.getObservation())
                .petDto(PetMapper.mapPetDto(adoptionEntity.getPet()))
                .build();
    }
}