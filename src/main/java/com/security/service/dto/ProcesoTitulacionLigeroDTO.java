package com.security.service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProcesoTitulacionLigeroDTO {

    private Boolean grupo;
    private Double calificacionFinal;
    private LocalDateTime fechaDefensa;
    private Double notaLector1;
    private Double notaLector2;

    private Double notaTribunal1;
    private String personaTribunal1;
    private Double notaTribunal2;
    private String personaTribunal2;
    private Integer tutorId;
    private Double notaTribunal3;
    private Double notaPropuestaProyecto;

    // public ProcesoTitulacionDTO() {} // Constructor vacío necesario

}