package com.security.service.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class ProcesoPagoDocenteDTO extends ProcesoDTO{
    private String modalidad;
    private Integer materiaId;
    private LocalDate fechaInicioClase;
    private LocalDate fechaFinClase;
    // private Integer cohorte;
    // public ProcesoPagoDocenteDTO() {} // Constructor vacío necesario
}
