package com.security.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class PasoValidacionDTO {
    private boolean esValido;
    private String mensaje;
    private String responsableNombreCompleto;
    private String responsableCorreo;
    private String responsableTelefono;
    private LocalDateTime fechaInicio;
    private String estado;
    private String observacion;
    private List<PasoDTO> pasosEnCurso;

}