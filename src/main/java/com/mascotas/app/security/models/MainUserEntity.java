package com.mascotas.app.security.models;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MainUserEntity implements UserDetails{


	private Long id;
	private String firstName;
	private String username;
	private String email;
	private String password;

	//Cambia roles por authorities:Spring Security
	private Collection<? extends GrantedAuthority> authorities;

	//Se sobrescriben todos los métodos : UserDetails
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	
	//Solo han sido cambiados a : true
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	//Personalizado : Agregado
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	//Se generan los constructores
	public MainUserEntity(Long id, String firstName, String username, String email, String password,
						  Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	//Se genera el método Build : Obtener usuario
	public static MainUserEntity build(UserEntity userEntity) {
		//1. Obtener los roles
		//2. Convertirlos a authorities
		//(Se convierte la clase rol a la clase GrantedAuthority)
		Set<RoleEntity> rolesUsuario = userEntity.getRoles();
		List<GrantedAuthority> authorities = rolesUsuario.stream().map(rol->new SimpleGrantedAuthority(rol.getRoleName().name())).collect(Collectors.toList());

		//FormaSimplificada:
		//List<GrantedAuthority> authorities = usuarioModel.getRoles()stream().map(rol->new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());;
		
		return new MainUserEntity(userEntity.getId(),
				userEntity.getFirstName(),
				userEntity.getUsername(),
				userEntity.getEmail(),
				userEntity.getPassword(),
				authorities);
	}

}