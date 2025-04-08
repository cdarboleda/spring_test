package com.security.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.security.db.ObservacionLector;
import com.security.db.ProcesoTitulacion;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class ProcesoCompletoTitulacionDTO extends ProcesoCompletoDTO {

    private Boolean grupo;
    private Double calificacionFinal;
    private LocalDateTime fechaDefensa;
    private Double notaLector1;
    private Double notaLector2;
    private List<ObservacionLector> observaciones;

    public ProcesoCompletoTitulacionDTO(ProcesoTitulacion procesoTitulacion) {
        this.grupo = procesoTitulacion.getGrupo();
        this.calificacionFinal = procesoTitulacion.getCalificacionFinal();
        this.fechaDefensa = procesoTitulacion.getFechaDefensa();
        this.notaLector1 = procesoTitulacion.getNotaLector1();
        this.notaLector2 = procesoTitulacion.getNotaLector2();
        this.observaciones = procesoTitulacion.getObservaciones();
    }

}
