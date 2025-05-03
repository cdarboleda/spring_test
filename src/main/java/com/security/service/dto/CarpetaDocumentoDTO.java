package com.security.service.dto;

import lombok.Data;

@Data
//Para modificar
public class CarpetaDocumentoDTO {

    private Integer id;
    private String url;
    private Integer procesoId;//Para saber a que proceso insertarle
    private Integer pasoId;//Para saber a que persona insertarle
    private String estado;

}
