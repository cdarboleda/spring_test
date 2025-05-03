package com.security.service.dto;

import java.time.Instant;
import java.util.List;

import com.security.db.ObservacionLector;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class ProcesoTitulacionDTO extends ProcesoDTO{

    private Boolean grupo;
    private Double calificacionFinal;
    private Instant fechaDefensa;
    private Double notaLector1;
    private Double notaLector2;
    private List<ObservacionLector> observaciones;

    // public ProcesoTitulacionDTO() {} // Constructor vacío necesario
    
}
