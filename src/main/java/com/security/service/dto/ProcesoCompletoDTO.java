package com.security.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
//si necesito la informacion completa con relaciones del proceso
//la entidad normal no me da esto, por el fetch lazy
public class ProcesoCompletoDTO{
    private Integer id;
    private String descripcion;
    private String tipoProceso;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean finalizado;
    private List<CarpetaDocumentoLigeroDTO> carpetasDocumento;
    private PersonaLigeroDTO requiriente;
    private List<PasoDTO> pasos;
}
