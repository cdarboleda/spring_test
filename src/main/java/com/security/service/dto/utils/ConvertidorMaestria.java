package com.security.service.dto.utils;

import org.springframework.stereotype.Component;

import com.security.db.Maestria;
import com.security.service.dto.MaestriaDTO;

@Component
public class ConvertidorMaestria {

    public Maestria convertirAEntidad(MaestriaDTO maestriaDTO){
        Maestria maestria = new Maestria();
        maestria.setId(maestriaDTO.getId());
        maestria.setNombre(maestriaDTO.getNombre());
        maestria.setCodigo(maestriaDTO.getNombre());
        maestria.setCohorte(maestriaDTO.getCohorte());
        maestria.setFechaInicio(maestriaDTO.getFechaInicio());
        maestria.setFechaFin(maestriaDTO.getFechaFin());
        maestria.setEstado(maestriaDTO.getEstado());
        return maestria;
    }
    public MaestriaDTO convertirADTO(Maestria maestria){
        MaestriaDTO dto = new MaestriaDTO();
        dto.setId(maestria.getId());
        dto.setNombre(maestria.getNombre());
        dto.setCodigo(maestria.getCodigo());
        dto.setCohorte(maestria.getCohorte());
        dto.setFechaInicio(maestria.getFechaInicio());
        dto.setFechaFin(maestria.getFechaFin());
        dto.setEstado(maestria.getEstado());
        return dto;
    }

}
