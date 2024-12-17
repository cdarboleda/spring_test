package com.security.service.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import com.security.db.Paso;

import lombok.Data;

@Data
//si necesito la informacion completa con relaciones del proceso
//la entidad normal no me da esto, por el fetch lazy
public class ProcesoCompletoDTO{
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean estado;
    private List<DocumentoLigeroDTO> documentos;
    private PersonaLigeroDTO requiriente;
    private HashSet<PersonaLigeroDTO> personasProceso;
    private List<Paso> procesoPasos;
}
