package com.security.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.security.db.enums.Estado;
import com.security.db.enums.TipoProceso;

import lombok.Data;

@Data
public class MiProcesoDTO {
    private Integer procesoId;
    private TipoProceso tipoProceso;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean finalizado;
    private Boolean cancelado;

    private Integer requirienteId;
    private String requirienteCedula;
    private String requirienteNombre;
    private String requirienteApellido;

    private String pasoNombre;
    private String pasoEstado;
    private String pasoDescripcionEstado;
    private Integer responsablePasoId;
    private String responsablePasoCedula;

public MiProcesoDTO(){

}

    public MiProcesoDTO(
            Integer procesoId,
            TipoProceso tipoProceso,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            Boolean finalizado,
            Boolean cancelado,
            Integer personaId,
            String personaCedula,
            String pasoNombre,
            Integer responsablePasoId,
            String responsablePasoCedula) {
        this.procesoId = procesoId;
        this.tipoProceso = tipoProceso;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.finalizado = finalizado;
        this.cancelado = cancelado;
        this.requirienteId = personaId;
        this.requirienteCedula = personaCedula;
        this.pasoNombre = pasoNombre;
        this.responsablePasoId = responsablePasoId;
        this.responsablePasoCedula = responsablePasoCedula;
    }

    public MiProcesoDTO(
        Integer procesoId,
        TipoProceso tipoProceso,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Boolean finalizado,
        Boolean cancelado,
        Integer personaId,
        String personaCedula,
        String personaNombre,
        String personaApellido,
        String pasoNombre,
        String pasoEstado,
        String pasoDescripcionEstado,
        Integer responsablePasoId,
        String responsablePasoCedula) {
    this.procesoId = procesoId;
    this.tipoProceso = tipoProceso;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.finalizado = finalizado;
    this.cancelado = cancelado;
    this.requirienteId = personaId;
    this.requirienteCedula = personaCedula;
    this.requirienteNombre = personaNombre;
    this.requirienteApellido = personaApellido;
    this.pasoNombre = pasoNombre;
    this.pasoEstado = pasoEstado;
    this.pasoDescripcionEstado = pasoDescripcionEstado;
    this.responsablePasoId = responsablePasoId;
    this.responsablePasoCedula = responsablePasoCedula;
}

    // Getters y Setters
}
