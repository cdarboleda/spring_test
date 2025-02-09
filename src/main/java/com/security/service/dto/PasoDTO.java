package com.security.service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PasoDTO {
    private Integer id;
    private String nombre;
    private Integer orden;
    private String descripcionPaso;
    private String descripcionEstado;
    private LocalDateTime fechaInicio;
    private String estado;
    private LocalDateTime fechaFin;
    private String observacion;
    private String rol;

    
    private Integer idProceso;
    private Integer idResponsable;

    public PasoDTO(){}
    public PasoDTO(Integer id, String nombre, Integer idResponsable, String rol){
        this.id = id;
        this.nombre = nombre;
        this.idResponsable = idResponsable;
        this.rol = rol;
    }
    
}
