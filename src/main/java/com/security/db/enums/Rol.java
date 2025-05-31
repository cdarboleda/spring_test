package com.security.db.enums;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.security.exception.CustomException;

public enum Rol {
    DOCENTE("Docente"),
    SECRETARIA("Secretaria"),
    DIRECTOR("Director"),
    COORDINADOR("Coordinador"),
    ESTUDIANTE("Estudiante"),
    DISENADOR("Diseñador");


    private final String alias;

    Rol(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return super.toString();
    }

    public String getAlias() {
        return this.alias;
    }

    public static Rol fromNombre(String nombre) {
        for (Rol rol : Rol.values()) {
            if (rol.getNombre().equals(nombre)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Rol no encontrado para nombre: " + nombre);
    }

    public static Set<Rol> obtenerRolesFromNombre(List<String> rolesString) {
        Set<Rol> roles = new HashSet<>();

        rolesString.stream().forEach(rolString -> {
            try {
                roles.add(Rol.fromNombre(rolString));
            } catch (IllegalArgumentException e) {
                throw new CustomException("Rol inválido: " + rolString, HttpStatus.BAD_REQUEST);
            }
        });
        return roles;
    }
}
