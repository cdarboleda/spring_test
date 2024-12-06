package com.security.service.dto.utils;

import org.springframework.stereotype.Component;

import com.security.db.Proceso;
import com.security.service.dto.ProcesoCompletoDTO;
import com.security.service.dto.ProcesoLigeroDTO;

@Component
public class ConvertidorProceso {

    public ProcesoCompletoDTO convertirACompletoDTO(Proceso proceso) {

        ProcesoCompletoDTO procesoDTO = new ProcesoCompletoDTO();
        procesoDTO.setId(proceso.getId());
        procesoDTO.setNombre(proceso.getNombre());
        procesoDTO.setDescripcion(proceso.getDescripcion());
        procesoDTO.setFechaInicio(proceso.getFechaInicio());
        procesoDTO.setFechaFin(proceso.getFechaFin());
        procesoDTO.setEstado(proceso.getEstado());
        return procesoDTO;
    }

    public ProcesoLigeroDTO convertirALigeroDTO(Proceso proceso) {

        ProcesoLigeroDTO procesoDTO = new ProcesoLigeroDTO();
        procesoDTO.setId(proceso.getId());
        procesoDTO.setNombre(proceso.getNombre());
        procesoDTO.setDescripcion(proceso.getDescripcion());
        procesoDTO.setFechaInicio(proceso.getFechaInicio());
        procesoDTO.setFechaFin(proceso.getFechaFin());
        procesoDTO.setEstado(proceso.getEstado());
        return procesoDTO;
    }

}
