package com.security.service.dto;


import java.util.List;

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

    private List<Integer> roles;

    private Integer responsableId;
    
}
