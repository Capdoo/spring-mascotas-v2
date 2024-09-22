package com.mascotas.app.modules.searches;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascotas.app.utils.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/searchs")
public class SearchesController {
	@Autowired
	SearchResource searchResource;

	@PostMapping(value = "/create")
	public ResponseEntity<Object> createSearch(@RequestBody SearchDTO searchDTO, BindingResult bindingResult) throws JsonProcessingException {
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
		}
		return searchResource.createSearch(searchDTO);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<Object> readSearchs(){
		return searchResource.read();
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<Object> readSearch(@PathVariable(value = "id") Long id){
		return searchResource.readSeach(id);
	}

	@GetMapping("/read/pet/{id}")
	public ResponseEntity<Object> readSearchByPetId(@PathVariable(value = "id") Long petId){
		return searchResource.readByPetId(petId);
	}

	//update
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> updateSearch(@PathVariable(value = "id") Long id, @RequestBody SearchDTO searchDTO){
		return searchResource.updateSearch(id, searchDTO);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Object> deleteSearch(@PathVariable(value = "id") Long id){
		return searchResource.deleteSearch(id);
	}

	private String formatMessage(BindingResult bindingResult) throws JsonProcessingException {
		List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
				.map(err -> {
					Map<String, String> error = new HashMap<>();
					error.put(err.getField(), err.getDefaultMessage());
					return error;
				}).collect(Collectors.toList());
		ErrorMessageUtil errorMessage = ErrorMessageUtil.builder()
				.code("01")
				.messages(errors).build();

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = objectMapper.writeValueAsString(errorMessage);

		}catch (JsonProcessingException e){
			e.printStackTrace();
		}
		return jsonString;
	}
}