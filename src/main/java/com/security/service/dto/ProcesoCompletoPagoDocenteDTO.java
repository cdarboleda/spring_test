package com.security.service.dto;

import com.security.db.Materia;
import com.security.db.ProcesoPagoDocente;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class ProcesoCompletoPagoDocenteDTO extends ProcesoCompletoDTO{
    private Boolean modalidadVirtual;
    private Materia materia;

    public ProcesoCompletoPagoDocenteDTO(ProcesoPagoDocente pagoDocente) {
        this.modalidadVirtual=pagoDocente.getModalidadVirtual();
        this.materia = pagoDocente.getMateria();
    }
}
