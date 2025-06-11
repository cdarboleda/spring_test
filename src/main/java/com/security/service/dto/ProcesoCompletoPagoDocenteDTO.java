package com.security.service.dto;

import java.time.LocalDate;

import com.security.db.ProcesoPagoDocente;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class ProcesoCompletoPagoDocenteDTO extends ProcesoCompletoDTO{
    private Boolean modalidadVirtual;
    private MateriaTablaDTO materia;
    private LocalDate fechaInicioClase;
    private LocalDate fechaFinClase;
    private PersonaLigeroDTO coordinador;

    public ProcesoCompletoPagoDocenteDTO(ProcesoPagoDocente pagoDocente, MateriaTablaDTO materiaDTO, PersonaLigeroDTO coordinador) {
        this.modalidadVirtual=pagoDocente.getModalidadVirtual();
        this.materia = materiaDTO;
        this.fechaInicioClase = pagoDocente.getFechaInicioClase();
        this.fechaFinClase = pagoDocente.getFechaFinClase();
        this.coordinador = coordinador;
        // this.cohorte = pagoDocente.getCohorte();
    }
}
