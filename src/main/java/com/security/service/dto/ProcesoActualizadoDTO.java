package com.security.service.dto;

import com.security.db.enums.TipoEventoProceso;

import lombok.Data;

@Data
public class ProcesoActualizadoDTO {

    public ProcesoActualizadoDTO(Integer procesoId, String mensaje, TipoEventoProceso tipoEvento){
        this.procesoId = procesoId;
        this.mensaje = mensaje;
        this.tipoEvento = tipoEvento;
    }

    private Integer procesoId;
    private String mensaje;
    private TipoEventoProceso tipoEvento;
}

