package com.security.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.security.db.Materia;
import com.security.db.ProcesoPagoDocente;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class ProcesoCompletoPagoDocenteDTO extends ProcesoCompletoDTO{
    private Boolean modalidadVirtual;
    private Materia materia;
    private LocalDate fechaInicioClase;
    private LocalDate fechaFinClase;
    // private Integer cohorte;

    public ProcesoCompletoPagoDocenteDTO(ProcesoPagoDocente pagoDocente) {
        this.modalidadVirtual=pagoDocente.getModalidadVirtual();
        this.materia = pagoDocente.getMateria();
        this.fechaInicioClase = pagoDocente.getFechaInicioClase();
        this.fechaFinClase = pagoDocente.getFechaFinClase();
        // this.cohorte = pagoDocente.getCohorte();
    }
}
