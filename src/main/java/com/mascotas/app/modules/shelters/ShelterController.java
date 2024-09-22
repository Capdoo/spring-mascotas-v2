package com.mascotas.app.modules.shelters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelters")
public class ShelterController {

    @Autowired
    ShelterResource shelterResource;

//	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@PostMapping(value = "/create")
	public ResponseEntity<Object> createShelter(@RequestBody ShelterDto shelterDTO){
        return this.shelterResource.createShelter(shelterDTO);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<Object> readShelters(){
        return this.shelterResource.readShelters();
	}

	@GetMapping(value = "/read/{id}")
    public ResponseEntity<Object> readShelter(@PathVariable(value = "id") Long shelterId) {
        return this.shelterResource.readShelter(shelterId);
    }

	@GetMapping(value = "/read/pet/{id}")
	public ResponseEntity<Object> readSheltersByPetId(@PathVariable(value = "id") Long id) {
		return shelterResource.readSheltersByPetId(id);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> updateShelter(@PathVariable(value = "id") Long id, @RequestBody ShelterDto shelterDto){
		return shelterResource.updateShelter(id, shelterDto);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Object> deleteShelter(@PathVariable(value = "id") Long id) {
		return shelterResource.deleteShelter(id);
	}

}