package com.security.service.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotBlank
    @NotNull
    private String apellido;
    @NotBlank
    @NotNull
    private String cedula;
    @NotBlank
    @NotNull
    private String correo;
    @NotBlank
    @NotNull    
    private String telefono;
    @NotNull
    private Boolean activo;
    private String observacion;
    @NotEmpty(message = "Debe proporcionar al menos un rol")
    @NotNull
    //Un set (no se repiten) de los ids de los roles
    private List<String> roles;

}
