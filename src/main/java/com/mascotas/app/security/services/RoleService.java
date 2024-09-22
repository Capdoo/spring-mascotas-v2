package com.mascotas.app.security.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.security.enums.RoleName;
import com.mascotas.app.security.models.RoleEntity;
import com.mascotas.app.security.repositories.RoleRepository;

@Service
@Transactional
public class RoleService {
	@Autowired
    RoleRepository roleRepository;
	
	public Optional<RoleEntity> getByRoleName (RoleName roleName){
		return roleRepository.findByRoleName(roleName);
	}
	
	public void save(RoleEntity roleEntity) {
		roleRepository.save(roleEntity);
	}
}
