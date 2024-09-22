package com.mascotas.app.modules.pets;

import java.io.IOException;
import java.text.ParseException;

import com.mascotas.app.utils.ErrorMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/pets")
public class PetController {

	@Autowired
	PetResource petResource;

	@PostMapping(value = "/create")
	public ResponseEntity<Object> createPet(@RequestBody PetDto petDTO, @RequestHeader("Authorization") String token, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageUtil.formatMessage(bindingResult));
		}
		return this.petResource.createPet(petDTO, token);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<Object> readPets(){
		return this.petResource.read();
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<Object> readPet(@PathVariable(value = "id") Long id){
		return this.petResource.readPet(id);
	}

	@GetMapping(value = "/read/owner")
	public ResponseEntity<Object> readPetsByOwner(@RequestHeader(name = "Authorization") String token) throws ParseException {
		return this.petResource.readPetsByOwner(token);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> updatePet(@PathVariable(value = "id") Long id, @RequestBody PetDto petDto, @RequestHeader(value = "Authorization") String token){
		return this.petResource.updatePet(id, petDto, token);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Object> deletePet(@PathVariable(value = "id") Long id){
		return this.petResource.deletePet(id);
	}
}