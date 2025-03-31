package com.security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import com.security.db.Persona;
import com.security.db.ProcesoTitulacion;
import com.security.repo.ICarpetaDocumentoRepository;
import com.security.repo.IProcesoTitulacionRepository;
import com.security.service.IPersonaService;
import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.PersonaTitulacionLigeroDTO;
import com.security.service.dto.TitulacionPropuestaLigeroDTO;

@Service
@Transactional
public class ProcesoTitulacionServiceImpl implements IProcesoTitulacionService {

    private static final String ROL_TUTOR = "TUTOR";
    private static final String ROL_REVISOR = "REVISOR";
    private static final String ROL_LECTOR = "LECTOR";

    @Autowired
    private IProcesoTitulacionRepository procesoTitulacionRepository;

    @Autowired
    private IPersonaService personaService;

    // Obtener el revisor y la nota de la tabla titualcionProceso a partir del id
    @Override
    public TitulacionPropuestaLigeroDTO buscarRevisorYNota(Integer idProcesoTitulacion) {
        // TODO Auto-generated method stub
        Optional<ProcesoTitulacion> procesoTitulacion = this.procesoTitulacionRepository.findById(idProcesoTitulacion);
        TitulacionPropuestaLigeroDTO titulacionPropuestaLigeroDTO = new TitulacionPropuestaLigeroDTO();
        titulacionPropuestaLigeroDTO.setNombre(procesoTitulacion.get().getRevisorPropuestaProyecto());
        titulacionPropuestaLigeroDTO.setCalificacionPlan(procesoTitulacion.get().getNotaPropuestaProyecto());

        return titulacionPropuestaLigeroDTO;
    }

    @Override

    public void AgregarTutorRevisor(PersonaTitulacionLigeroDTO persona) {
        Persona personaVerificada = verificarDatosRevisorProyecto(persona);
        if (personaVerificada == null) {
            throw new EntityNotFoundException("No existe el proceso con ID: " + persona.getProcesoId() +
                    " o la persona con ID: " + persona.getId());
        }
        String nombrePersonaCompleto = nombreCompleto(personaVerificada);

        for (String rol : persona.getRoles()) {
            switch (rol.toUpperCase()) {
                case ROL_REVISOR:

                    this.procesoTitulacionRepository.insertarRevisorProyecto(persona.getProcesoId(),
                            nombrePersonaCompleto);
                    break;
                case ROL_TUTOR:
                    this.procesoTitulacionRepository.insertarTutorProyecto(persona.getProcesoId(),
                            nombrePersonaCompleto);
                    break;
                case ROL_LECTOR:
                    this.procesoTitulacionRepository.insertarTutorProyecto(persona.getProcesoId(),
                            nombrePersonaCompleto);
                    break;
                default:
                    throw new IllegalArgumentException("Rol no válido: " + rol);
            }
        }
    }

    private Persona verificarDatosRevisorProyecto(PersonaTitulacionLigeroDTO persona) {

        if (!this.procesoTitulacionRepository.existsByIdAndPersonaId(persona.getProcesoId(), persona.getId())) {
            throw new EntityNotFoundException("El proceso con ID " + persona.getProcesoId() +
                    " o la persona con ID " + persona.getId() + " no existen.");
        }
        // quizas añadir verificacion si estan activos
        // quizas añadir verificacion si son rol Docentes
        return this.personaService.findById(persona.getId());
    }

    private String nombreCompleto(Persona persona) {
        return persona.getNombre() + " " + persona.getApellido();
    }

}
