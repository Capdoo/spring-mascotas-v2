package com.mascotas.app.security.repositories;

import java.util.Optional;

import com.mascotas.app.security.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findByUsernameOrEmail(String username, String email);
	Optional<UserEntity> findById(long id);
//	Optional<UserEntity> findByDni(String dni);
	Optional<UserEntity> findByTokenPassword(String tokenPassword);

	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	boolean existsByUsernameOrEmail(String username, String email);
//	boolean existsByDni(String dni);
}
