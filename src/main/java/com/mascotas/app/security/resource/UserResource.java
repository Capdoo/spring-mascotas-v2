package com.mascotas.app.security.resource;

import com.mascotas.app.security.dto.UserDTO;
import com.mascotas.app.security.mapper.SecurityMapper;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserResource {

    @Autowired
    UserService userService;

    public ResponseEntity<Object> read() {
        List<UserEntity> listUsersEntity = userService.findAllUsers();
        List<UserDTO> listUsers = listUsersEntity.stream().map(
                SecurityMapper::mapUserDto
        ).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listUsers);

    }

    public ResponseEntity<Object> readUser(Long id) {
        if(!userService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        UserEntity userEntity = userService.readUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(SecurityMapper.mapUserDto(userEntity));
    }

    public ResponseEntity<Object> updateUser(Long id, UserDTO userDTO) {
        if(!userService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        userDTO.setId(id);
        UserEntity userEntity = userService.updateUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(SecurityMapper.mapUserDto(userEntity));
    }

    public ResponseEntity<Object> deleteUser(Long id) {
        if(!userService.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        UserEntity userEntity = userService.deleteUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(SecurityMapper.mapUserDto(userEntity));
    }

}
