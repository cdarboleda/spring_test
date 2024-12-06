package com.security.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
//Para cambios con el front
public class ProcesoDTO {
    private Integer id;
    private String nombre;
    private Integer requirienteId;
    private List<Integer> personasId;
    private LocalDateTime fechaFinal;//Solo serviria cuando vamos a actualizar el final
    private Boolean estado;//Solo serviria cuando vamos a actualizar el final

}
