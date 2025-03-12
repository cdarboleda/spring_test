package com.security.service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PasoDTO {
    private Integer id;
    private String nombre;
    private Integer orden;
    private String descripcionEstado;
    private LocalDateTime fechaInicio;
    private String estado;
    private LocalDateTime fechaFin;
    private String observacion;
    private String rol;

    
    private Integer idProceso;
    private Integer idResponsable;
    private String responsablePasoCedula;
    private String responsableNombre;
    private String responsableApellido;

    public PasoDTO(){}
    public PasoDTO(Integer id, String nombre, Integer idResponsable, String rol){
        this.id = id;
        this.nombre = nombre;
        this.idResponsable = idResponsable;
        this.rol = rol;
    }

    public PasoDTO(String nombre, String estado, String descripcionEstado, LocalDateTime fechaInicio, Integer responsableId, String responsablePasoCedula){

        this.nombre = nombre;
        this.estado = estado;
        this.descripcionEstado = descripcionEstado;
        this.fechaInicio = fechaInicio;
        this.idResponsable = responsableId;
        this.responsablePasoCedula = responsablePasoCedula;
    }    
}
