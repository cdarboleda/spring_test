package com.security.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class ProcesoPagoDocenteDTO extends ProcesoDTO{
    private Boolean modalidadVirtual;
    private Integer materiaId;
    // public ProcesoPagoDocenteDTO() {} // Constructor vacío necesario
}
