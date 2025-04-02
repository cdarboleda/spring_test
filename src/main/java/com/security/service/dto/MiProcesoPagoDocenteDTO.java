package com.security.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.security.db.enums.TipoProceso;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MiProcesoPagoDocenteDTO extends MiProcesoDTO {

    private String materiaCodigo;
    private String maestriaCodigo;
    private String materiaNombre;
    private String maestriaNombre;

    private LocalDate fechaInicioClase;
    private LocalDate fechaFinClase;

    public MiProcesoPagoDocenteDTO(
            Integer procesoId, TipoProceso tipoProceso, LocalDateTime fechaInicio,
            LocalDateTime fechaFin, Boolean finalizado, Boolean cancelado,
            Integer requirienteId, String requirienteCedula,
            String requirienteNombre, String requirienteApellido,
            String pasoNombre, String pasoEstado, String pasoDescripcionEstado,
            LocalDateTime pasoFechaInicio, Integer responsablePasoId,
            String responsablePasoCedula, String materiaCodigo, String materiaNombre, String maestriaCodigo,
            String maestriaNombre, LocalDate fechaInicioClase, LocalDate fechaFinClase) {

        super(procesoId, tipoProceso, fechaInicio, fechaFin, finalizado, cancelado,
                requirienteId, requirienteCedula, requirienteNombre, requirienteApellido,
                pasoNombre, pasoEstado, pasoDescripcionEstado, pasoFechaInicio,
                responsablePasoId, responsablePasoCedula);

        this.materiaCodigo = materiaCodigo;
        this.materiaNombre = materiaNombre;
        this.maestriaCodigo = maestriaCodigo;
        this.maestriaNombre = maestriaNombre;
        this.fechaInicioClase = fechaInicioClase;
        this.fechaFinClase = fechaFinClase;

    }

}
