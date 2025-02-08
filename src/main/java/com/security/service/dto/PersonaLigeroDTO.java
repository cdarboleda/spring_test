package com.security.service.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.security.db.Rol;

import lombok.Data;

@Data
public class PersonaLigeroDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String telefono;
    private List<String> roles;
    public PersonaLigeroDTO(){}

    public PersonaLigeroDTO(Integer id, String cedula, String nombre, String apellido, List<String> roles){
        this.id=id;
        this.cedula=cedula;
        this.nombre=nombre;
        this.apellido=apellido;
        this.roles = roles;
        // this.roles = roles.stream()
        // .map(rol -> rol.getNombre())
        // .collect(Collectors.toList());

    }

    public PersonaLigeroDTO(Integer id, String cedula, String nombre, String apellido){
        this.id=id;
        this.cedula=cedula;
        this.nombre=nombre;
        this.apellido=apellido;
    }
}
