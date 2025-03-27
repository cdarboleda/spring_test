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
        materia.setCodigo(materiaDTO.getCodigo());
        materia.setNombre(materiaDTO.getNombre());
        materia.setPeriodo(materiaDTO.getPeriodo());
        return materia;
    }

    public MateriaTablaDTO convertirEntidadATablaDTO(Materia materia) {
        MateriaTablaDTO materiaTablaDTO = new MateriaTablaDTO();
        materiaTablaDTO.setId(materia.getId());
        materiaTablaDTO.setCodigo(materia.getCodigo());
        materiaTablaDTO.setNombre(materia.getNombre());
        materiaTablaDTO.setPeriodo(materia.getPeriodo());
        materiaTablaDTO.setMaestriaId(materia.getMaestriaDetalle().getId());
        materiaTablaDTO.setMaestriaNombre(materia.getMaestriaDetalle().getMaestria().getNombre());
        materiaTablaDTO.setMaestriaCodigo(materia.getMaestriaDetalle().getMaestria().getCodigo());
        materiaTablaDTO.setMaestriaCohorte(materia.getMaestriaDetalle().getCohorte());
        materiaTablaDTO.setMaestriaFechaEjecucionDesde(materia.getMaestriaDetalle().getFechaInicio());
        materiaTablaDTO.setMaestriaFechaEjecucionHasta(materia.getMaestriaDetalle().getFechaFin());
        return materiaTablaDTO;
    }

}
