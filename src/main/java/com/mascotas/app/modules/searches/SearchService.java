package com.mascotas.app.modules.searches;

import com.mascotas.app.modules.pets.PetEntity;

import java.util.List;

public interface SearchService {

    public List<SearchEntity> listAllSearchs();
    //crud
    public SearchEntity createSearch(SearchDTO searchDTO);
    public SearchEntity readSearch(Long id);
    public SearchEntity updateSearch(SearchDTO searchDTO);
    public SearchEntity deleteSearch(SearchDTO searchDTO);

    //business rules
    public SearchEntity readSearchByPet(PetEntity petEntity);
//    public List<SearchEntity> radAllSearchsByOwner(OwnerEntity ownerEntity);

    public Boolean existsByPet(PetEntity petEntity);

    //public Boolean existsById();

    //Not ready
    //public List<SearchEntity> radAllSearchsByShelter(ShelterEntity shelterModel);
}