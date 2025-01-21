package com.security.service.dto;

import com.security.db.ProcesoPagoDocente;

import lombok.Data;

@Data
public class ProcesoCompletoPagoDocenteDTO extends ProcesoCompletoDTO{
    private Boolean modalidadVirtual;

    public ProcesoCompletoPagoDocenteDTO(ProcesoPagoDocente pagoDocente) {
        this.modalidadVirtual=pagoDocente.getModalidadVirtual();
    }
}
