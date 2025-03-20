package com.security.service.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class ProcesoPagoDocenteDTO extends ProcesoDTO{
    private Boolean modalidadVirtual;
    private Integer materiaId;
    private LocalDate fechaEjecucionDesde;
    private LocalDate fechaEjecucionHasta;
    private Integer cohorte;
    // public ProcesoPagoDocenteDTO() {} // Constructor vacío necesario
}
