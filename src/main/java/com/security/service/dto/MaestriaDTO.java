package com.security.service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MaestriaDTO {

    private Integer maestriaId;
    private Integer maestriaDetalleId;

    private String nombre;
    private String codigo;

    private String cohorte;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;

}
