package com.mascotas.app.security.mapper;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.dto.UserDTO;
import com.mascotas.app.security.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityMapper {

    public static UserDTO mapUserDto(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setAddress(userEntity.getAddress());
//        userDTO.setDni(userEntity.getDni());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPhone1(userEntity.getPhone1());
        userDTO.setPhone2(userEntity.getPhone2());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setState(userEntity.getState());
        return userDTO;
    }
}