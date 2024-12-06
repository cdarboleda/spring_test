package com.security.service.dto;

import lombok.Data;

@Data
public class PersonaLigeroDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String password;
}
