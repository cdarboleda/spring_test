package com.security.service.dto;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class PersonaDTO {

    private Integer id;

    private String nombre;
   
    private String apellido;

    private String cedula;

    private String correo;
    
    private String telefono;
   
    private String password;

    private Set<Integer> roles = new HashSet<>();

    private Integer responsableId;

    
}
