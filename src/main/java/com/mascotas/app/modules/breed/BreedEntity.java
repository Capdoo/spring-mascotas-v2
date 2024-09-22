package com.mascotas.app.modules.breed;

import com.mascotas.app.modules.pets.PetEntity;
import com.mascotas.app.modules.species.SpeciesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ptts_breeds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BreedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(cascade =  CascadeType.ALL, mappedBy = "breed")
    private List<PetEntity> pets;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "species_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "BREED_FK_SPECIES"))
    private SpeciesEntity species;

    private Integer state;
}