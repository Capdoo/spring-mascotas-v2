package com.mascotas.app.email.controllers;

import com.mascotas.app.dto.MessageDTO;
import com.mascotas.app.email.dto.ChangePasswordDTO;
import com.mascotas.app.email.dto.EmailValuesDTO;
import com.mascotas.app.email.services.EmailService;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import com.mascotas.app.security.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class EmailController {

    @Autowired
    EmailService emailService;
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;


    private static final String emailSubject = "Change password";

    @PostMapping("/send-email")
    public ResponseEntity<Object> sendEmailTemplate(@RequestBody EmailValuesDTO emailValuesDTO){

        Optional<UserEntity> usuarioModelOptional = userServiceImp.getByUsernameOrEmail(emailValuesDTO.getMailTo());
        if(!usuarioModelOptional.isPresent()){
            return new ResponseEntity<Object>(new MessageDTO("No user found with these credentials"), HttpStatus.NOT_FOUND);
        }
        UserEntity usuarioModel = usuarioModelOptional.get();

        emailValuesDTO.setMailFrom(emailFrom);
        emailValuesDTO.setSubject(emailSubject);
        emailValuesDTO.setMailTo(usuarioModel.getEmail());
        emailValuesDTO.setUserName(usuarioModel.getUsername());

        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        emailValuesDTO.setToken(tokenPassword);
        usuarioModel.setTokenPassword(tokenPassword);

        //TO DO
        //userServiceImp.save(usuarioModel);
        //userRepository.save(usuarioModel);

        emailService.sendEmailTemplate(emailValuesDTO);

        return ResponseEntity.ok(new MessageDTO("Email sent successfully"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<Object>(new MessageDTO("Wrong fields"), HttpStatus.BAD_REQUEST);
        }
        if(!changePasswordDTO.getPassword().equals(changePasswordDTO.getConfirmPassword())){
            return new ResponseEntity<Object>(new MessageDTO("Passwords do not match"), HttpStatus.BAD_REQUEST);
        }
        Optional<UserEntity> usuarioModelOptional = userServiceImp.getByTokenPassword(changePasswordDTO.getTokenPassword());
        if(!usuarioModelOptional.isPresent()){
            return new ResponseEntity<Object>(new MessageDTO("User not found"), HttpStatus.NOT_FOUND);
        }
        UserEntity usuarioModel = usuarioModelOptional.get();
        String newPassword = passwordEncoder.encode(changePasswordDTO.getPassword());
        usuarioModel.setPassword(newPassword);
        usuarioModel.setTokenPassword(null);
        //TO DO
        //userRepository.save(usuarioModel);

        return new ResponseEntity<Object>(new MessageDTO("Password updated"), HttpStatus.OK);
    }
}
