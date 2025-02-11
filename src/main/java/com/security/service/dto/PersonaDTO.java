package com.security.service.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//Para inserciones modificaciones un dto con roles
@Data
public class PersonaDTO {

    private Integer id;

    private String idKeycloak;

    @NotBlank
    @NotNull
    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String telefono;
    private Boolean activo;
    //Un set (no se repiten) de los ids de los roles
    private List<String> roles;

}
