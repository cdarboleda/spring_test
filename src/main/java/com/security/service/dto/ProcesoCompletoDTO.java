package com.security.service.dto;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
//si necesito la informacion completa con relaciones del proceso
//la entidad normal no me da esto, por el fetch lazy
public class ProcesoCompletoDTO{
    private Integer id;
    private String descripcion;
    private String tipoProceso;
    private Instant fechaInicio;
    private Instant fechaFin;
    private Boolean finalizado;
    private List<CarpetaDocumentoLigeroDTO> carpetasDocumento;
    private PersonaLigeroDTO requiriente;
    private List<PasoDTO> pasos;
}
