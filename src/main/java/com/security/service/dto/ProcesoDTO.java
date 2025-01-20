package com.security.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.security.db.enums.TipoProceso;

import lombok.Data;

@Data
//Para cambios con el front
public class ProcesoDTO {
    private Integer id;
    private String nombre;
    private Integer requirienteId;
    private String descripcion;
    private String tipoProceso;

    private List<String> personasId;
    private LocalDateTime fechaFinal;//Solo serviria cuando vamos a actualizar el final
    private Boolean finalizado;//Solo serviria cuando vamos a actualizar el final

}
