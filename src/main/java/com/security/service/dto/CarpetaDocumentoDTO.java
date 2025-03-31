package com.security.service.dto;

import lombok.Data;

@Data
// Para modificar
public class CarpetaDocumentoDTO {

    private Integer id;
    private String url;
    private Integer procesoId;// Para saber a que proceso insertarle
    private Integer pasoId;// Para saber a que persona insertarle
    private String estado;
    private String descripcionEstado; // pasa comprobar si el estado requiere actualizacion
    // public DocumentoDTO(){

    // }
    // public DocumentoDTO(Integer id,
    // String nombre,
    // String url,
    // String descripcion,
    // LocalDateTime fechaCreacion,
    // Integer procesoId,
    // String procesoNombre) {

    // this.id = id;
    // this.nombre = nombre;
    // this.url = nombre;
    // this.descripcion = descripcion;
    // this.fechaCreacion = fechaCreacion;
    // this.procesoId = procesoId;
    // this.procesoNombre = procesoNombre;
    // }
}
