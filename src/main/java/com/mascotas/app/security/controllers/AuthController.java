package com.mascotas.app.security.controllers;

import java.io.IOException;
import java.text.ParseException;

import com.mascotas.app.security.resource.SecurityResource;
import com.mascotas.app.security.services.UserServiceImp;
import com.mascotas.app.utils.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascotas.app.security.dto.JwtDTO;
import com.mascotas.app.security.dto.LoginUserDTO;
import com.mascotas.app.security.dto.NewUserDTO;
import com.mascotas.app.security.jwt.JwtProvider;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserServiceImp userServiceImp;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	SecurityResource securityResource;
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody NewUserDTO newUserDTO, BindingResult bindingResult) throws IOException{
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageUtil.formatMessage(bindingResult));
		}
		if (userServiceImp.existsByUsername(newUserDTO.getUsername())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Already Exists");
		}
		if (userServiceImp.existsByEmail(newUserDTO.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already Exists");
		}

		return securityResource.registerUser(newUserDTO);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginUserDTO loginUserDTO, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong fields");
		}
		if(!userServiceImp.existsByUsernameOrEmail(loginUserDTO.getUsername())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}

        return securityResource.authentication(loginUserDTO.getUsername(), loginUserDTO.getPassword());
	}

	@PostMapping("/refresh")
	public ResponseEntity<Object> refreshToken(@RequestBody JwtDTO jwtDTO) throws ParseException {
		return securityResource.refreshToken(jwtDTO);
	}
}