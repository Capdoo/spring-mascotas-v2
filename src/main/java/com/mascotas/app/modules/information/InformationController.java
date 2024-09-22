package com.mascotas.app.modules.information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/information")
public class InformationController {

    @Autowired
    InformationResource informationResource;

    @GetMapping(value = "/districts")
    public ResponseEntity<Object> readDistrictsLima() {
        return informationResource.readDistricts();
    }

    @GetMapping(value = "/genres")
    public ResponseEntity<Object> readGenres() {
        return informationResource.readGenres();
    }

}
