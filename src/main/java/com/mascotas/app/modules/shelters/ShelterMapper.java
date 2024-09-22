package com.mascotas.app.modules.shelters;

import com.mascotas.app.files.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;

public class ShelterMapper {

    @Autowired
    static FileUploadService fileUploadService;

    public static ShelterDto mapShelterDto(ShelterEntity shelterEntity) {
        return ShelterDto.builder()
                .id(shelterEntity.getId())
                .address(shelterEntity.getAddress())
                .district(shelterEntity.getDistrict())
                .name(shelterEntity.getName())
                .numberOfPartners(shelterEntity.getNumberOfPartners())
                .contactNumber(shelterEntity.getContactNumber())
                .registerDate(shelterEntity.getRegisterDate().toString())
                .idMainPartner(shelterEntity.getId())
                .dniMainPartner(shelterEntity.getDistrict())
//                .encoded(fileUploadService.convertBytesToEncoded(shelterEntity.getLinkImg()))
                .urlLink(shelterEntity.getLinkImg())
                .build();
    }

}
