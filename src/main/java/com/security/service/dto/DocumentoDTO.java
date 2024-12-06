package com.security.service.dto;

import lombok.Data;

@Data
//Para modificar
public class DocumentoDTO {

    private Integer id;
    private String nombre;
    private String url;
    private String descripcion;
    private Integer procesoId;//Para saber a que proceso insertarle
    // public DocumentoDTO(){
        
    // }
    // public DocumentoDTO(Integer id,
    //     String nombre,
    //     String url,
    //     String descripcion,
    //     LocalDateTime fechaCreacion,
    //     Integer procesoId,
    //     String procesoNombre) {
        
    //     this.id = id;
    //     this.nombre = nombre;
    //     this.url = nombre;
    //     this.descripcion = descripcion;
    //     this.fechaCreacion = fechaCreacion;
    //     this.procesoId = procesoId;
    //     this.procesoNombre = procesoNombre;
    // }
}
