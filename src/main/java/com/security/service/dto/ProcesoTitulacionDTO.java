package com.security.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.security.db.ObservacionLector;

import lombok.Data;

@Data
public class ProcesoTitulacionDTO extends ProcesoDTO{

    private Boolean grupo;
    private Double calificacionFinal;
    private LocalDateTime fechaDefensa;
    private Double notaLector1;
    private Double notaLector2;
    private List<ObservacionLector> observaciones;

    // public ProcesoTitulacionDTO() {} // Constructor vacío necesario
    
}
