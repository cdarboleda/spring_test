package com.security.service;

import com.security.service.dto.PersonaDTO;
import com.security.service.dto.PersonaTitulacionLigeroDTO;
import com.security.service.dto.ProcesoCompletoTitulacionDTO;
import com.security.service.dto.ProcesoTitulacionDTO;
import com.security.service.dto.ProcesoTitulacionLigeroDTO;

public interface IProcesoTitulacionService {

        public Object insertarProcesoTitulacion(ProcesoTitulacionDTO procesoTitulacionDTO);

        public void asignarSecretariaAlproceso(ProcesoCompletoTitulacionDTO proceso);

        public ProcesoTitulacionLigeroDTO actualizarProcesoTitulacion(Integer id,
                        ProcesoTitulacionLigeroDTO procesoTitulacionDTO);

        public ProcesoTitulacionLigeroDTO obtenerProcesoTitulacion(Integer id);

        public PersonaTitulacionLigeroDTO buscarTutorProcesoTitulacion(Integer idProcesoTitulacion);

        public Integer obtenerCantidadProcesosTitulacionTutor(Integer idPersona);

        // public LocalDateTime buscarFechaDefensa(Integer idProcesoTitulacion);

}
