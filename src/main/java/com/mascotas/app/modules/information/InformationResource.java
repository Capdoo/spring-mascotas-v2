package com.mascotas.app.modules.information;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InformationResource {

    String[] districts = new String[]{"Ancón", "Ate", "Barranco", "Breña", "Carabayllo", "Chaclacayo", "Chorrillos", "Cieneguilla", "Comas", "El Agustino", "Independencia",  "Jesús María", "La Molina", "La Victoria", "Lima (Cercado de Lima)", "Lince", "Los Olivos", "Lurigancho (Chosica)", "Lurín", "Magdalena del Mar", "Miraflores", "Pachacámac", "Pucusana", "Pueblo Libre (Magdalena Vieja)", "Puente Piedra", "Punta Hermosa", "Punta Negra", "Rímac", "San Bartolo", "San Borja", "San Isidro", "San Juan de Lurigancho", "San Juan de Miraflores", "San Luis", "San Martín de Porres", "San Miguel", "Santa Anita", "Santa María del Mar", "Santa Rosa", "Santiago de Surco", "Surquillo", "Villa El Salvador", "Villa María del Triunfo"};

    String[] genres = new String[]{"1;Macho", "2;Hembra"};

    public ResponseEntity<Object> readDistricts() {
        List<InformationDto> listSend = new ArrayList<>();
        List<String> listDistricts = Arrays.asList(this.districts);

        listSend = listDistricts.stream().map(
                e -> new InformationDto("1", e)
        ).collect(Collectors.toList());

        return ResponseEntity.ok(listSend);
    }

    public ResponseEntity<Object> readGenres() {
        List<InformationDto> listSend = new ArrayList<>();
        List<String> listDistricts = Arrays.asList(this.genres);

        listSend = listDistricts.stream().map(
                e -> new InformationDto(e.split(";")[0], e.split(";")[1])
        ).collect(Collectors.toList());

        return ResponseEntity.ok(listSend);
    }

}