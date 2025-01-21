package com.security.service.dto;

import java.time.LocalDateTime;

import com.security.db.Persona;
import com.security.db.Proceso;

import lombok.Data;

@Data
public class PasoCompletoDTO {
    private Integer id;
    private String nombre;
    private Integer orden;
    private String descripcionPaso;
    private String descripcionEstado;
    private LocalDateTime fechaInicio;
    private String estado;
    private LocalDateTime fechaFin;
    private Proceso proceso;
    private Persona responsable;
}
