package com.security.db.enums;

public enum Rol {
    DOCENTE("docente", "Docente"),
    SECRETARIA("secretaria", "Secretaria"),
    DIRECTOR("director", "Director"),
    COORDINADOR("coordinador", "Coordinador"),
    ESTUDIANTE("estudiante", "Estudiante"),
    DISENADOR("disenador", "Diseñador");


    private final String nombre;
    private final String alias;

    Rol(String nombre, String alias) {
        this.nombre = nombre;
        this.alias = alias;
    }

    public String getNombre(){
        return this.nombre;
    }
    public String getAlias(){
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
}
