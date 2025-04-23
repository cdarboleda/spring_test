package com.security.service.dto;

import java.time.LocalDateTime;

import com.security.db.enums.Evento;

import jakarta.persistence.Column;

import lombok.Data;

@Data

public class ProcesoLogDTO {

    private Integer id;

    private Integer responsableId;

    private String responsableNombre;

    private Integer pasoOrden;

    private String pasoNombre;

    private String pasoEstado;

    private Evento tipoEvento;

    private String pasoEstadoDescripcion;

    private String pasoObservacion;

    private LocalDateTime fechaCambio;

}
