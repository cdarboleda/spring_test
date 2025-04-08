package com.security.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.security.db.ObservacionLector;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class ProcesoTitulacionDTO extends ProcesoDTO {

    private Boolean grupo;
    private Double calificacionFinal;
    private LocalDateTime fechaDefensa;
    private Double notaLector1;
    private Double notaLector2;
    private String tutorPoyecto;
    private Double notaPropuestaProyecto;

    // public ProcesoTitulacionDTO() {} // Constructor vacío necesario

}
