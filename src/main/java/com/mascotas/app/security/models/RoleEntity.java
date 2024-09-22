package com.mascotas.app.security.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mascotas.app.security.enums.RoleName;
import lombok.Data;

@Entity
@Table(name="ptts_roles")
@Data
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private RoleName roleName;

	public RoleEntity() {
		super();
	}

	public RoleEntity(RoleName roleName) {
		super();
		this.roleName = roleName;
	}
}
