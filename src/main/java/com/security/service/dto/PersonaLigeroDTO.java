package com.security.service.dto;

import lombok.Data;

@Data
public class PersonaLigeroDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String telefono;
    public PersonaLigeroDTO(){}

    public PersonaLigeroDTO(Integer id, String cedula, String nombre, String apellido){
        this.id=id;
        this.cedula=cedula;
        this.nombre=nombre;
        this.apellido=apellido;
    }
}
