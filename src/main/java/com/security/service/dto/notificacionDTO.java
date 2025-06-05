package com.security.service.dto;

import com.security.db.enums.TipoEventoNotificacion;
import com.security.db.enums.TipoObjetoNotificacion;

import lombok.Data;

@Data
public class notificacionDTO {

    public notificacionDTO(TipoObjetoNotificacion tipoObjetoNotificacion, Integer procesoId, String mensaje, TipoEventoNotificacion tipoEvento){
        this.tipoObjetoNotificacion = tipoObjetoNotificacion;
        this.procesoId = procesoId;
        this.mensaje = mensaje;
        this.tipoEvento = tipoEvento;
    }

    private TipoObjetoNotificacion tipoObjetoNotificacion;
    private Integer procesoId;
    private String mensaje;
    private TipoEventoNotificacion tipoEvento;

}

