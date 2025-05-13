package com.security.service.dto.utils;

import org.springframework.stereotype.Component;

import com.security.db.Materia;
import com.security.service.dto.MateriaDTO;
import com.security.service.dto.MateriaTablaDTO;

@Component
public class ConvertidorMateria {

    public Materia convertirAEntidad(MateriaDTO materiaDTO) {
        Materia materia = new Materia();
        materia.setId(materiaDTO.getId());
        materia.setNombre(materiaDTO.getNombre());
        materia.setPeriodo(materiaDTO.getPeriodo());
        return materia;
    }

    public MateriaTablaDTO convertirEntidadATablaDTO(Materia materia) {
        MateriaTablaDTO materiaTablaDTO = new MateriaTablaDTO();
        materiaTablaDTO.setId(materia.getId());
        materiaTablaDTO.setNombre(materia.getNombre());
        materiaTablaDTO.setPeriodo(materia.getPeriodo());
        materiaTablaDTO.setMaestriaId(materia.getMaestria().getId());
        materiaTablaDTO.setMaestriaNombre(materia.getMaestria().getNombre());
        materiaTablaDTO.setMaestriaCodigo(materia.getMaestria().getCodigo());
        materiaTablaDTO.setMaestriaCohorte(materia.getMaestria().getCohorte());
        materiaTablaDTO.setMaestriaFechaEjecucionDesde(materia.getMaestria().getFechaInicio());
        materiaTablaDTO.setMaestriaFechaEjecucionHasta(materia.getMaestria().getFechaFin());
        return materiaTablaDTO;
    }

}
