package com.mascotas.app.modules.shelters;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.models.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class ShelterServiceImpl implements ShelterService{

    @Autowired
    ShelterRepository shelterRepository;

    @Autowired
    FileUploadService fileUploadService;

    @Override
    public List<ShelterEntity> listAllShelters() {
        return shelterRepository.findAll();
    }

    @Override
    public ShelterEntity createShelter(ShelterDto shelterDto, UserEntity userEntity) {

        //UsuarioModel usuarioRepresentante = usuarioRepository.findById(refugioDTO.getIdRepresentante()).get();
//        UserEntity usuarioRepresentante = userRepository.findByDni(shelterDTO.getDniMainPartner()).get();

        ShelterEntity refugioNuevo = new ShelterEntity();
        refugioNuevo.setAddress(shelterDto.getAddress());
        refugioNuevo.setDistrict(shelterDto.getDistrict());
        refugioNuevo.setRegisterDate(new Timestamp(System.currentTimeMillis()));
        refugioNuevo.setName(shelterDto.getName());
        refugioNuevo.setNumberOfPartners(shelterDto.getNumberOfPartners());
        refugioNuevo.setContactNumber(shelterDto.getContactNumber());
        //change
//        refugioNuevo.setPartners(null);

        try {
            String encoded = fileUploadService.obtenerEncoded(shelterDto.getEncoded());
            byte[] imagen = fileUploadService.convertEncodedToBytes(encoded);
            String url = fileUploadService.fileUpload(imagen);
            refugioNuevo.setLinkImg(url);
        }catch (Exception e){
            e.printStackTrace();
        }

        return shelterRepository.save(refugioNuevo);
    }

    @Override
    public ShelterEntity readShelter(Long id) {
        return shelterRepository.findById(id).orElse(null);
    }

    @Override
    public ShelterEntity updateShelter(ShelterDto shelterDto) {
        ShelterEntity shelterUpdate;

        shelterUpdate = readShelter(shelterDto.getId());
        if (shelterUpdate == null) {
            return null;
        }

        shelterUpdate.setAddress(shelterDto.getAddress());
        shelterUpdate.setName(shelterDto.getName());
        shelterUpdate.setContactNumber(shelterDto.getContactNumber());
        shelterUpdate.setDistrict(shelterDto.getDistrict());
        shelterUpdate.setNumberOfPartners(shelterDto.getNumberOfPartners());

        return shelterRepository.save(shelterUpdate);
    }

    @Override
    public ShelterEntity deleteShelter(ShelterDto shelterDto) {
        ShelterEntity shelterEntity;
        shelterEntity = readShelter(shelterDto.getId());
        shelterEntity.setState(0);
        return shelterRepository.save(shelterEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

}
