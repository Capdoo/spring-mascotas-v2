package com.mascotas.app.security.resource;

import com.mascotas.app.dto.MessageDTO;
import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.dto.JwtDto;
import com.mascotas.app.security.dto.NewUserDTO;
import com.mascotas.app.security.dto.UserDTO;
import com.mascotas.app.security.jwt.JwtProvider;
import com.mascotas.app.security.mapper.SecurityMapper;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.services.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class SecurityResource {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    FileUploadService fileUploadService;


    public ResponseEntity<Object> registerUser(NewUserDTO newUserDTO) {
        UserEntity userEntity;
        UserDTO userDTO;

        newUserDTO.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        userEntity = userServiceImp.createUser(newUserDTO);

        userDTO = SecurityMapper.mapUserDto(userEntity);
        userDTO.setEncoded(fileUploadService.convertBytesToEncoded(userEntity.getImage()));
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    public ResponseEntity<Object> authentication(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            JwtDto jwtDto = new JwtDto(jwt);
            return ResponseEntity.status(HttpStatus.OK).body(jwtDto);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong fields");
        }
    }

    public ResponseEntity<Object> refreshToken(JwtDto jwtDTO) {
        try {
            String token = jwtProvider.refreshToken(jwtDTO);
            JwtDto jwt = new JwtDto(token);
            return new ResponseEntity<Object>(jwt, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<Object>(new MessageDTO(e.getMessage()), HttpStatus.OK);
        }
    }

}
