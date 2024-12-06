package com.security.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DocumentoLigeroDTO {
    private Integer id;
    private String nombre;
    private String url;
    private String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaCreacion;
}
