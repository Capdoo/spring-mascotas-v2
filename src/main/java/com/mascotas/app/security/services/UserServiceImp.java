package com.mascotas.app.security.services;

import java.util.*;

import javax.transaction.Transactional;

import com.mascotas.app.files.FileUploadService;
import com.mascotas.app.security.dto.NewUserDTO;
import com.mascotas.app.security.enums.RoleName;
import com.mascotas.app.security.models.RoleEntity;
import com.mascotas.app.security.models.UserEntity;
import com.mascotas.app.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascotas.app.security.dto.UserDTO;

@Service
//Para implementar rollbacks y evitar incoherencia : Concurrencia
@Transactional
public class UserServiceImp implements UserService{
	@Autowired
	RoleService roleService;
	@Autowired
	UserRepository userRepository;
	@Autowired
    FileUploadService fileUploadService;

	@Override
	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity createUser(NewUserDTO newUserDTO) {
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(roleService.getByRoleName(RoleName.ROLE_OWNER).get());
		if (newUserDTO.getRoles().contains("admin")) {
			roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
		}
		if (newUserDTO.getRoles().contains("publisher")) {
			roles.add(roleService.getByRoleName(RoleName.ROLE_PUBLISHER).get());
		}

		String encoded = "";
		encoded = newUserDTO.getEncoded() == null ? fileUploadService.getEncodedDefault() : fileUploadService.obtenerEncoded(newUserDTO.getEncoded());
		byte[] image = fileUploadService.convertEncodedToBytes(encoded);

		UserEntity createUserEntity = UserEntity.builder()
				.username(newUserDTO.getUsername())
//				.dni(newUserDTO.getDni())
				.firstName(newUserDTO.getFirstName())
				.lastName(newUserDTO.getLastName())
				.phone1(newUserDTO.getPhone1())
				.phone2(newUserDTO.getPhone2())
				.province(newUserDTO.getProvince())
				.district(newUserDTO.getDistrict())
				.address(newUserDTO.getAddress())
				.email(newUserDTO.getEmail())
				.password(newUserDTO.getPassword())
				.image(image)
				.roles(roles)
				.state(1).build();
		return userRepository.save(createUserEntity);
	}

	@Override
	public UserEntity readUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public UserEntity updateUser(UserDTO userDTO) {
		UserEntity userEntity = readUser(userDTO.getId());
		userEntity.setLastName(userDTO.getLastName());
		userEntity.setFirstName(userDTO.getFirstName());
//		userEntity.setDni(userDTO.getDni());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setAddress(userDTO.getAddress());
		userEntity.setUsername(userDTO.getUsername());
		userEntity.setPhone1(userDTO.getPhone1());
		userEntity.setPhone2(userDTO.getPhone2());
		userEntity.setImage(fileUploadService.convertEncodedToBytes(userDTO.getEncoded()));

		return userRepository.save(userEntity);
	}

	@Override
	public UserEntity deleteUser(UserDTO userDTO) {
		UserEntity userDB = readUser(userDTO.getId());
		userDB.setState(0);
		return userRepository.save(userDB);
	}

	@Override
	public Boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

	//Security
	public Optional<UserEntity> getByUsernameOrEmail(String usernameOrEmail){
		return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
	}

	public Optional<UserEntity> getByTokenPassword(String tokenPassword){
		return userRepository.findByTokenPassword(tokenPassword);
	}

	@Override
	public Boolean existsByUsername(String nombreUsuario) {
		return userRepository.existsByUsername(nombreUsuario);
	}

	public boolean existsByUsernameOrEmail(String usernameOrEmail) {
		return userRepository.existsByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public UserEntity readByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

}