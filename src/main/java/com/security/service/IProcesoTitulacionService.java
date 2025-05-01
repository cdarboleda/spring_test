package com.security.service;

import java.time.LocalDateTime;
import java.util.List;

import com.security.service.dto.PersonaDTO;
import com.security.service.dto.PersonaTitulacionLigeroDTO;
import com.security.service.dto.ProcesoCompletoTitulacionDTO;
import com.security.service.dto.ProcesoTitulacionDTO;
import com.security.service.dto.ProcesoTitulacionLigeroDTO;
import com.security.service.dto.TitulacionResponsableNotaLigeroDTO;

public interface IProcesoTitulacionService {

        public Object insertarProcesoTitulacion(ProcesoTitulacionDTO procesoTitulacionDTO);

        public void asignarSecretariaAlproceso(ProcesoCompletoTitulacionDTO proceso);

        public ProcesoTitulacionLigeroDTO actualizarProcesoTitulacion(Integer id,
                        ProcesoTitulacionLigeroDTO procesoTitulacionDTO);

        public ProcesoTitulacionLigeroDTO obtenerProcesoTitulacion(Integer id);

        public void insertarNotaPasoEspecifico(Integer idProcesoTitulacion,
                        TitulacionResponsableNotaLigeroDTO responsableNotaLigeroDTO);

        public void agregarTutorProcesoTitulacion(Integer idProcesoTitulacion, PersonaDTO personaTutorDTO);

        public PersonaTitulacionLigeroDTO buscarTutorProcesoTitulacion(Integer idProcesoTitulacion);

        public void agregarPersonaTribunaUno(Integer idProcesoTitulacion, PersonaDTO personaTribunalUnoDTO);

        public List<PersonaTitulacionLigeroDTO> buscarPersonasTribunal(Integer idProcesoTitulacion);

        public void agregarPersonaTribunalDos(Integer idProcesoTitulacion, PersonaDTO personaTribunalDosDTO);

        public void actualizarFechaDefensa(Integer idProcesoTitulacion, LocalDateTime fechaDefensa);

        public LocalDateTime buscarFechaDefensa(Integer idProcesoTitulacion);

}
