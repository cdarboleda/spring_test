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
    private String descripcionEstado;
    private LocalDateTime pasoFechaInicio;
    private LocalDateTime pasoFechaFin;
    private Integer pasoOrden;
    private Integer responsableId;
    private String responsableNombre;
    private String responsableCedula;
    private String responsableApellido;
    private String responsableTelefono;
    private String responsableCorreo;
    private Integer carpetaId;
    private String carpetaUrl;
    private String observacion;
    private String rol;

    public ProcesoPasoDocumentoDTO(Integer procesoId, String procesoDescripcion,
            Integer pasoId, String pasoNombre, String pasoDescripcion,
            Estado pasoEstado, String descripcionEstado, LocalDateTime pasoFechaInicio,
            LocalDateTime pasoFechaFin, Integer pasoOrden, String observacion, String rol,
            Integer responsableId, String responsableNombre,
            String responsableCedula, String responsableApellido,String responsableTelefono,String responsableCorreo,
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
        this.responsableTelefono = responsableTelefono;
        this.responsableCorreo = responsableCorreo;
        this.carpetaId = carpetaId;
        this.carpetaUrl = carpetaUrl;
        this.rol = rol;
        this.observacion = observacion;
        this.descripcionEstado = descripcionEstado;
    }

}
