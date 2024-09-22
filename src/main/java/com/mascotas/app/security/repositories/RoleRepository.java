package com.mascotas.app.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mascotas.app.security.enums.RoleName;
import com.mascotas.app.security.models.RoleEntity;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer>{
	
	Optional<RoleEntity> findByRoleName(RoleName roleName);
	boolean existsByRoleName(RoleName roleName);
}