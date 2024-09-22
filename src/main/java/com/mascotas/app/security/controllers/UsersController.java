package com.mascotas.app.security.controllers;

import com.mascotas.app.security.dto.UserDTO;

import com.mascotas.app.security.resource.UserResource;
import com.mascotas.app.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService userService;
    @Autowired
    UserResource userResource;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/read")
    public ResponseEntity<Object> read(){
        return this.userResource.read();
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Object> read(@PathVariable(value = "id") Long id){
        return this.userResource.readUser(id);
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
//    @GetMapping(value = "/read/owners")
//    public ResponseEntity<Object> read(@PathVariable(value = "id") Long id){
//        return this.userResource.readUser(id);
//    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserDTO userDTO){
        return this.userResource.updateUser(id, userDTO);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id){
        return this.userResource.deleteUser(id);
    }

}