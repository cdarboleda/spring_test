package com.security.service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
//Si necesito solo la informacion unica del proceso
public class ProcesoLigeroDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer requirienteId;
    private Boolean finalizado;
    private String tipoProceso;

}
