package com.security.service.dto;


import java.time.LocalDateTime;

import com.security.db.enums.Estado;

import lombok.Data;

@Data
public class ProcesoPasoDocumentoDTO {

    private Integer procesoId;
    private String procesoDescripcion;
    private Integer pasoId;
    private String pasoNombre;
    private String pasoDescripcion;
    private Estado pasoEstado;
    private LocalDateTime pasoFechaInicio;
    private LocalDateTime pasoFechaFin;
    private Integer pasoOrden;
    private Integer responsableId;
    private String responsableNombre;
    private String responsableCedula;
    private String responsableApellido;
    private Integer carpetaId;
    private String carpetaUrl;

    public ProcesoPasoDocumentoDTO(Integer procesoId, String procesoDescripcion,
            Integer pasoId, String pasoNombre, String pasoDescripcion,
            Estado pasoEstado, LocalDateTime pasoFechaInicio,
            LocalDateTime pasoFechaFin, Integer pasoOrden,
            Integer responsableId, String responsableNombre,
            String responsableCedula, String responsableApellido,
            Integer carpetaId, String carpetaUrl) {
        this.procesoId = procesoId;
        this.procesoDescripcion = procesoDescripcion;
        this.pasoId = pasoId;
        this.pasoNombre = pasoNombre;
        this.pasoDescripcion = pasoDescripcion;
        this.pasoEstado = pasoEstado;
        this.pasoFechaInicio = pasoFechaInicio;
        this.pasoFechaFin = pasoFechaFin;
        this.pasoOrden = pasoOrden;
        this.responsableId = responsableId;
        this.responsableNombre = responsableNombre;
        this.responsableCedula = responsableCedula;
        this.responsableApellido = responsableApellido;
        this.carpetaId = carpetaId;
        this.carpetaUrl = carpetaUrl;
    }

}
