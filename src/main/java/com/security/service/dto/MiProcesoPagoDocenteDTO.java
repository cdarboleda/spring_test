package com.security.service.dto;

import java.time.Instant;
import java.time.LocalDate;

import com.security.db.enums.TipoProceso;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MiProcesoPagoDocenteDTO extends MiProcesoDTO {

    private String maestriaCodigo;
    private String materiaNombre;
    private Integer materiaHoras;
    private String maestriaNombre;

    private LocalDate fechaInicioClase;
    private LocalDate fechaFinClase;

    private Integer cohorte;
    private String estado;

    public MiProcesoPagoDocenteDTO(
            Integer procesoId, TipoProceso tipoProceso, Instant fechaInicio,
            Instant fechaFin, Boolean finalizado, Boolean cancelado,
            Integer requirienteId, String requirienteCedula,
            String requirienteNombre, String requirienteApellido,
            String pasoNombre, String pasoEstado, String pasoDescripcionEstado,
            Instant pasoFechaInicio, Integer responsablePasoId,
            String responsableCedula, String materiaNombre, Integer materiaHoras, String maestriaCodigo,
            String maestriaNombre, LocalDate fechaInicioClase, LocalDate fechaFinClase, Integer cohorte, String estado) {

        super(procesoId, tipoProceso, fechaInicio, fechaFin, finalizado, cancelado,
                requirienteId, requirienteCedula, requirienteNombre, requirienteApellido,
                pasoNombre, pasoEstado, pasoDescripcionEstado, pasoFechaInicio,
                responsablePasoId, responsableCedula);

        this.materiaNombre = materiaNombre;
        this.materiaHoras = materiaHoras;
        this.maestriaCodigo = maestriaCodigo;
        this.maestriaNombre = maestriaNombre;
        this.fechaInicioClase = fechaInicioClase;
        this.fechaFinClase = fechaFinClase;
        this.cohorte = cohorte;
        this.estado  = estado;

    }

}
