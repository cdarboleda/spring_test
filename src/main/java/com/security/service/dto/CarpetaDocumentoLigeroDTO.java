package com.security.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CarpetaDocumentoLigeroDTO {
    private Integer id;
    private String url;
    private String tipo;
}
