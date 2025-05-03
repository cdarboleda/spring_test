package com.security.service.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class PasoDTO {
    private Integer id;
    private String nombre;
    private Integer orden;
    private String descripcionEstado;
    private Instant fechaInicio;
    private String estado;
    private Instant fechaFin;
    private String observacion;
    private String rol;

    private Integer idProceso;
    private String tipoProceso;
    private Integer idResponsable;
    private String responsableCedula;
    private String responsableNombre;
    private String responsableApellido;
    private String responsableCorreo;

    public PasoDTO() {
    }

    public PasoDTO(Integer id, String nombre, Integer idResponsable, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.idResponsable = idResponsable;
        this.rol = rol;
    }

    public PasoDTO(String nombre, String estado, String descripcionEstado, Instant fechaInicio,
            Integer responsableId, String responsableCedula) {

        this.nombre = nombre;
        this.estado = estado;
        this.descripcionEstado = descripcionEstado;
        this.fechaInicio = fechaInicio;
        this.idResponsable = responsableId;
        this.responsableCedula = responsableCedula;
    }

    public PasoDTO(String nombre, String estado, String descripcionEstado, Instant fechaInicio,
            Integer responsableId, String responsableCedula, String responsableCorreo) {

        this.nombre = nombre;
        this.estado = estado;
        this.descripcionEstado = descripcionEstado;
        this.fechaInicio = fechaInicio;
        this.idResponsable = responsableId;
        this.responsableCedula = responsableCedula;
        this.responsableCorreo = responsableCorreo;
    }
}
