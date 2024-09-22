package com.mascotas.app.modules.pets;

import java.util.List;


import com.mascotas.app.security.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity, Long>{

    List<PetEntity> findAllByUser(UserEntity userEntity);
}
