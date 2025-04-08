package com.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import com.security.db.Persona;
import com.security.db.ProcesoTitulacion;
import com.security.db.enums.PasoTitulacion;
import com.security.repo.IPersonaRepository;
import com.security.repo.IProcesoTitulacionRepository;
import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.TitulacionResponsableNotaLigeroDTO;
import com.security.service.dto.TitulacionTutorLigeroDTO;

@Service
@Transactional
public class ProcesoTitulacionServiceImpl implements IProcesoTitulacionService {

    @Autowired
    private IProcesoTitulacionRepository procesoTitulacionRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    public void insertarNotaPasoEspecifico(
            Integer idProcesoTitulacion, TitulacionResponsableNotaLigeroDTO responsableNotaLigeroDTO) {

        // Validaciones iniciales
        if (responsableNotaLigeroDTO == null || responsableNotaLigeroDTO.getNombrePaso() == null) {
            throw new IllegalArgumentException("El DTO de responsable o el nombre del paso no pueden ser nulos.");
        }

        // Buscar el proceso de titulación
        ProcesoTitulacion proceso = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

        PasoTitulacion paso = PasoTitulacion.fromString(responsableNotaLigeroDTO.getNombrePaso());
        if (paso == null) {
            throw new IllegalArgumentException(
                    "El paso de titulación no es válido: " + responsableNotaLigeroDTO.getNombrePaso());
        }

        Double calificacion = responsableNotaLigeroDTO.getCalificacionPaso();
        // Asignar la calificación según el paso
        switch (paso) {
            case REVISION_IDONEIDAD -> proceso.setNotaPropuestaProyecto(calificacion);
            case REVISION_LECTOR_1 -> proceso.setNotaLector1(calificacion);
            case REVISION_LECTOR_2 -> proceso.setNotaLector2(calificacion);
            default -> throw new IllegalArgumentException("El paso no permite la inserción de calificación.");
        }

        procesoTitulacionRepository.save(proceso);
    }

    @Override
    public void agregarTutorProcesoTitulacion(Integer idProcesoTitulacion, PersonaDTO personatutorDTO) {
        // TODO Auto-generated method stub

        ProcesoTitulacion procesoTitu = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

        Persona persona = this.personaRepository.findById(personatutorDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tutor con id: " + personatutorDTO.getId() + " no encontrado"));

        procesoTitu.setTutorPoyecto(persona.getNombre() + " " + persona.getApellido());

        procesoTitulacionRepository.save(procesoTitu);
    }

    @Override
    public TitulacionTutorLigeroDTO buscarTutorProcesoTitulacion(Integer idProcesoTitulacion) {

        ProcesoTitulacion procesoTitu = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));
        TitulacionTutorLigeroDTO tutorLigeroDTO = new TitulacionTutorLigeroDTO();
        tutorLigeroDTO.setNombreTutor(procesoTitu.getTutorPoyecto());
        tutorLigeroDTO.setIdProceso(procesoTitu.getId());

        return tutorLigeroDTO;

    }

}
