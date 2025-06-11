package com.security.service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
//para insertar materias
public class MateriaDTO {
    private Integer id;

    private String nombre;

    private Integer periodo;

    private Integer maestriaId;

    private Integer horas;

}
