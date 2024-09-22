package com.mascotas.app.modules.pets;


import com.mascotas.app.security.models.UserEntity;

import java.util.List;

public interface PetService {
    public List<PetEntity> listAllPets();
    //crud
    public PetEntity createPet(PetDto petDTO, String username);
    public PetEntity readPet(Long id);
    public PetEntity updatePet(PetDto petDTO);
    public PetEntity deletePet(PetDto petDTO);

    //Find by Owner
    public List<PetEntity> readPetByOwner(UserEntity userEntity);

    //Exists By Id
    public Boolean existsById(Long id);
}
