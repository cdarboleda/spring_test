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
    private Boolean finalizado;

    private Integer requirienteId;
    private String requirienteCedula;

    private String pasoNombre;
    private Integer responsablePasoId;
    private String responsablePasoCedula;

    public MiProcesoDTO(
            Integer procesoId,
            TipoProceso tipoProceso,
            LocalDateTime fechaInicio,
            Boolean finalizado,
            Integer personaId,
            String personaCedula,
            String pasoNombre,
            Integer responsablePasoId,
            String responsablePasoCedula) {
        this.procesoId = procesoId;
        this.tipoProceso = tipoProceso;
        this.fechaInicio = fechaInicio;
        this.finalizado = finalizado;
        this.requirienteId = personaId;
        this.requirienteCedula = personaCedula;
        this.pasoNombre = pasoNombre;
        this.responsablePasoId = responsablePasoId;
        this.responsablePasoCedula = responsablePasoCedula;
    }

    // Getters y Setters
}
