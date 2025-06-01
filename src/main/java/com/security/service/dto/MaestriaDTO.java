package com.security.service.dto;

import java.time.LocalDate;


import lombok.Data;

@Data
public class MaestriaDTO {

    private Integer id;

    private String nombre;
    private String codigo;

    private Integer cohorte;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

}
