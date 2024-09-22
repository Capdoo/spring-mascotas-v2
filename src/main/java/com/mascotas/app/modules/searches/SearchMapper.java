package com.mascotas.app.modules.searches;
import com.mascotas.app.utils.FechaUtil;
public class SearchMapper {

    public static SearchDTO mapSearchDto(SearchEntity searchEntity){
        return SearchDTO.builder()
                .id(searchEntity.getId())
                .address(searchEntity.getAddress())
                .district(searchEntity.getDistrict())
                .phoneA(searchEntity.getPhoneA())
                .phoneB(searchEntity.getPhoneB())
                .petId(searchEntity.getPet().getId())
                .namePet(searchEntity.getPet().getName())
//				.speciesPet(searchEntity.getPet().getDetail().getSpecies())
//				.breedPet(searchEntity.getPet().getDetail().getBreed())
                .colour(searchEntity.getPet().getColour())
                .lostDate(FechaUtil.getStrindDateFromTimestamp(searchEntity.getLostDate()))
                .registerDate(FechaUtil.getStrindDateFromTimestamp(searchEntity.getRegisterDate()))
                .message(searchEntity.getMessage())
//                .encoded(fileUploadService.convertBytesToEncoded(searchEntity.getPet().getImage()))
                .state(searchEntity.getState())
                .build();
    }

}
