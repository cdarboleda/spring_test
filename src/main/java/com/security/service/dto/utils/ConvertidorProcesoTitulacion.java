package com.security.service.dto.utils;

import org.springframework.stereotype.Component;

import com.security.db.ProcesoTitulacion;
import com.security.service.dto.ProcesoTitulacionLigeroDTO;
import com.security.service.dto.ProcesoTitulacionLigeroDTO;

@Component
public class ConvertidorProcesoTitulacion {

    public ProcesoTitulacionLigeroDTO convertirProcesoTitulacion(ProcesoTitulacion procesoTitulacion) {
        ProcesoTitulacionLigeroDTO ProcesoTitulacionLigeroDTO = new ProcesoTitulacionLigeroDTO();

        ProcesoTitulacionLigeroDTO.setGrupo(procesoTitulacion.getGrupo());
        ProcesoTitulacionLigeroDTO.setCalificacionFinal(procesoTitulacion.getCalificacionFinal());
        ProcesoTitulacionLigeroDTO.setFechaDefensa(procesoTitulacion.getFechaDefensa());
        ProcesoTitulacionLigeroDTO.setNotaLector1(procesoTitulacion.getNotaLector1());
        ProcesoTitulacionLigeroDTO.setNotaLector2(procesoTitulacion.getNotaLector2());

        ProcesoTitulacionLigeroDTO.setNotaTribunal1(procesoTitulacion.getNotaTribunal1());
        ProcesoTitulacionLigeroDTO.setPersonaTribunal1(procesoTitulacion.getPersonaTribunal1());
        ProcesoTitulacionLigeroDTO.setNotaTribunal2(procesoTitulacion.getNotaTribunal2());
        ProcesoTitulacionLigeroDTO.setPersonaTribunal2(procesoTitulacion.getPersonaTribunal2());
        ProcesoTitulacionLigeroDTO.setNotaTribunal3(procesoTitulacion.getNotaTribunal3());
        ProcesoTitulacionLigeroDTO.setTutorId(procesoTitulacion.getTutorId());
        // ProcesoTitulacionLigeroDTO.setNotaPropuestaProyecto(procesoTitulacion.getNotaPropuestaProyecto());

        return ProcesoTitulacionLigeroDTO;
    }

}
