package com.security.service.dto.utils;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.security.db.Paso;
import com.security.db.enums.Estado;
import com.security.service.dto.PasoDTO;

@Component
public class ConvertidorPaso {

    public PasoDTO convertirAPasoDTO(Paso paso){
        PasoDTO dto = new PasoDTO();

        dto.setId(paso.getId());
        dto.setNombre(paso.getNombre());
        dto.setOrden(paso.getOrden());
        dto.setDescripcionEstado(paso.getDescripcionEstado());
        dto.setEstado(paso.getEstado().name().toUpperCase());
        dto.setFechaInicio(paso.getFechaInicio());
        dto.setFechaFin(paso.getFechaFin());
        dto.setIdProceso(paso.getProceso().getId());
        dto.setIdResponsable((paso.getResponsable()!=null)?paso.getResponsable().getId():null);
        dto.setResponsableCedula((paso.getResponsable()!=null)?paso.getResponsable().getCedula():null);
        dto.setResponsableNombre((paso.getResponsable()!=null)?paso.getResponsable().getNombre():null);
        dto.setResponsableApellido((paso.getResponsable()!=null)?paso.getResponsable().getApellido():null);
        dto.setRol(paso.getRol().getNombre());
        return dto;
    }
    
    public Paso convertirAEntidad(Paso paso, PasoDTO pasoDTO){

        paso.setId(pasoDTO.getId());
        paso.setNombre(pasoDTO.getNombre());
        paso.setOrden(pasoDTO.getOrden());
        paso.setDescripcionEstado(pasoDTO.getDescripcionEstado());
        paso.setEstado(Estado.valueOf(pasoDTO.getEstado()));
        paso.setFechaFin(pasoDTO.getFechaFin());
        paso.setFechaInicio(pasoDTO.getFechaInicio());
        //persona y proceso es fuera 
        return paso;
    }


}
