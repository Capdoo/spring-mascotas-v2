package com.mascotas.app.utils;

//import com.mascotas.app.modules.details.DetailEntity;
//import com.mascotas.app.modules.details.DetailRepository;
import com.mascotas.app.security.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.mascotas.app.security.enums.RoleName;
import com.mascotas.app.security.models.RoleEntity;
import com.mascotas.app.security.services.RoleService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateRolesUtil implements CommandLineRunner{

	@Autowired
	RoleService roleService;
	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

		RoleEntity roleGuest = new RoleEntity(RoleName.ROLE_GUEST);
		RoleEntity rolePublisher = new RoleEntity(RoleName.ROLE_PUBLISHER);
		RoleEntity roleOwner = new RoleEntity(RoleName.ROLE_OWNER);
		RoleEntity roleShelterOwner = new RoleEntity(RoleName.ROLE_SHELTER_OWNER);
		RoleEntity roleAdmin = new RoleEntity(RoleName.ROLE_ADMIN);

		if(!roleRepository.existsByRoleName(RoleName.ROLE_GUEST)){
			roleService.save(roleGuest);
		}
		if(!roleRepository.existsByRoleName(RoleName.ROLE_PUBLISHER)){
			roleService.save(rolePublisher);
		}
		if(!roleRepository.existsByRoleName(RoleName.ROLE_OWNER)){
			roleService.save(roleOwner);
		}
		if(!roleRepository.existsByRoleName(RoleName.ROLE_SHELTER_OWNER)){
			roleService.save(roleShelterOwner);
		}
		if(!roleRepository.existsByRoleName(RoleName.ROLE_ADMIN)){
			roleService.save(roleAdmin);
		}
	}
}