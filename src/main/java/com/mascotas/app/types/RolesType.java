package com.mascotas.app.types;

public enum RolesType {

    ROLE_GUEST("ROLE_GUEST"),
    PUBLISHER("ROLE_PUBLISHER"),
    OWNER("ROLE_OWNER"),
    SHELTER_OWNER("ROLE_SHELTER_OWNER"),
    ADMIN("ROLE_ADMIN");

    private final String label;

    private RolesType(String label) {
        this.label = label;
    }

}
