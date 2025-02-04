package com.security.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.ToString;

public class PasoLigerDTO {
    private Integer id;
    private String nombre;
    private Integer orden;
    private String descripcionPaso;
    private String descripcionEstado;
    private LocalDateTime fechaInicio;
    private String estado;
    private LocalDateTime fechaFin;
    private String observacion;
    private Proceso idProceso;
    private Persona idResponsable;
    
}
