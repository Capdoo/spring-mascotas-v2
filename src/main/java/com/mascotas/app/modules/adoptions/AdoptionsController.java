package com.mascotas.app.modules.adoptions;

import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/adoptions")
public class AdoptionsController {

	@Autowired
	AdoptionResource adoptionResource;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createAdoption(@RequestBody AdoptionDto adoptionDTO){
		return this.adoptionResource.createAdoption(adoptionDTO);
	}
	
	@GetMapping(value = "/read")
	public ResponseEntity<Object> readAdoptions(){
		return this.adoptionResource.read();
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<Object> readAdoption(@PathVariable(value = "id") Long id){
		return this.adoptionResource.readAdoption(id);
	}

	@GetMapping(value = "/read/owner")
	public ResponseEntity<Object> readAdoptionsByOwner(@RequestHeader(name = "Authorization") String token) throws ParseException {
		return adoptionResource.readAdoptionsByOwner(token);
	}

	@GetMapping(value = "/read/pet/{id}")
	public ResponseEntity<Object> readAdoptionsByPetId(@PathVariable(value = "id") Long id){
		return adoptionResource.readAdoptionsByPetId(id);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> updateAdoption(@PathVariable(value = "id") Long id, @RequestBody AdoptionDto adoptionDto){
		return adoptionResource.updateAdoption(id, adoptionDto);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Object> deleteAdoption(@PathVariable(value = "id") Long id) {
		return adoptionResource.deleteAdoption(id);
	}

}
