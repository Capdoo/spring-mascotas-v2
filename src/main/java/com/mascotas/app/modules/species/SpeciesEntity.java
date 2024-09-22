package com.mascotas.app.modules.species;

import com.mascotas.app.modules.breed.BreedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ptts_species")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeciesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(cascade =  CascadeType.ALL, mappedBy = "species")
    private List<BreedEntity> breeds;

    private Integer state;
}