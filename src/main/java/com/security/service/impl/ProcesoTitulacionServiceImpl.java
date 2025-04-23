package com.security.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.security.service.dto.utils.Convertidor;
import com.security.service.dto.utils.ConvertidorProcesoTitulacion;
import com.security.db.Persona;
import com.security.db.ProcesoTitulacion;
import com.security.db.enums.PasoTitulacion;
import com.security.repo.IPersonaRepository;
import com.security.repo.IProcesoTitulacionRepository;
import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.TitulacionResponsableNotaLigeroDTO;
import com.security.service.dto.PersonaTitulacionLigeroDTO;
import com.security.service.dto.ProcesoTitulacionDTO;
import com.security.service.dto.ProcesoTitulacionLigeroDTO;

@Service
@Transactional
public class ProcesoTitulacionServiceImpl implements IProcesoTitulacionService {

        private final ConvertidorProcesoTitulacion convertidorProcesoTitulacion;

        private final Convertidor convertidor;

        @Autowired
        private IProcesoTitulacionRepository procesoTitulacionRepository;

        @Autowired
        private IPersonaRepository personaRepository;

        ProcesoTitulacionServiceImpl(Convertidor convertidor,
                        ConvertidorProcesoTitulacion convertidorProcesoTitulacion) {
                this.convertidor = convertidor;
                this.convertidorProcesoTitulacion = convertidorProcesoTitulacion;
        }

        @Override
        public void actualizarFechaDefensa(Integer idProcesoTitulacion, LocalDateTime fechaDefensa) {

                ProcesoTitulacion proceso = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

                proceso.setFechaDefensa(fechaDefensa);
                this.procesoTitulacionRepository.save(proceso);
        }

        @Override
        public ProcesoTitulacionLigeroDTO obtenerProcesoTitulacion(Integer id) {
                ConvertidorProcesoTitulacion convertidor = new ConvertidorProcesoTitulacion();
                return convertidor.convertirProcesoTitulacion(this.procesoTitulacionRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado")));
        }

        @Override
        public LocalDateTime buscarFechaDefensa(Integer idProcesoTitulacion) {
                // TODO Auto-generated method stub
                return this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"))
                                .getFechaDefensa();
        }

        @Override
        public ProcesoTitulacionLigeroDTO actualizarProcesoTitulacion(Integer idProcesoTitulacion,
                        ProcesoTitulacionLigeroDTO procesoTitulacionDTO) {
                ProcesoTitulacion procesoExistente = procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

                Optional.ofNullable(procesoTitulacionDTO.getCalificacionFinal())
                                .ifPresent(procesoExistente::setCalificacionFinal);
                Optional.ofNullable(procesoTitulacionDTO.getFechaDefensa())
                                .ifPresent(procesoExistente::setFechaDefensa);
                // Optional.ofNullable(procesoTitulacionDTO.getNotaPropuestaProyecto())
                // .ifPresent(procesoExistente::setNotaPropuestaProyecto);
                Optional.ofNullable(procesoTitulacionDTO.getNotaLector1()).ifPresent(procesoExistente::setNotaLector1);
                Optional.ofNullable(procesoTitulacionDTO.getNotaLector2()).ifPresent(procesoExistente::setNotaLector2);
                Optional.ofNullable(procesoTitulacionDTO.getTutorPoyecto())
                                .ifPresent(procesoExistente::setTutorPoyecto);
                Optional.ofNullable(procesoTitulacionDTO.getGrupo()).ifPresent(procesoExistente::setGrupo);

                Optional.ofNullable(procesoTitulacionDTO.getNotaTribunal1())
                                .ifPresent(procesoExistente::setNotaTribunal1);
                Optional.ofNullable(procesoTitulacionDTO.getPersonaTribunal1())
                                .ifPresent(procesoExistente::setPersonaTribunal1);
                Optional.ofNullable(procesoTitulacionDTO.getNotaTribunal2())
                                .ifPresent(procesoExistente::setNotaTribunal2);
                Optional.ofNullable(procesoTitulacionDTO.getPersonaTribunal2())
                                .ifPresent(procesoExistente::setPersonaTribunal2);

                // Guardar cambios
                procesoTitulacionRepository.save(procesoExistente);

                ConvertidorProcesoTitulacion convertidor = new ConvertidorProcesoTitulacion();
                ProcesoTitulacionLigeroDTO procesoDTO = convertidor.convertirProcesoTitulacion(procesoExistente);
                return procesoDTO;
        }

        public void insertarNotaPasoEspecifico(
                        Integer idProcesoTitulacion, TitulacionResponsableNotaLigeroDTO responsableNotaLigeroDTO) {

                // Validaciones iniciales
                if (responsableNotaLigeroDTO == null || responsableNotaLigeroDTO.getNombrePaso() == null) {
                        throw new IllegalArgumentException(
                                        "El DTO de responsable o el nombre del paso no pueden ser nulos.");
                }

                // Buscar el proceso de titulación
                ProcesoTitulacion proceso = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

                PasoTitulacion paso = PasoTitulacion.fromString(responsableNotaLigeroDTO.getNombrePaso());
                if (paso == null) {
                        throw new IllegalArgumentException(
                                        "El paso de titulación no es válido: "
                                                        + responsableNotaLigeroDTO.getNombrePaso());
                }

                Double calificacion = responsableNotaLigeroDTO.getCalificacionPaso();
                // Asignar la calificación según el paso
                switch (paso) {
                        // case REVISION_IDONEIDAD -> proceso.setNotaPropuestaProyecto(calificacion);
                        case REVISION_LECTOR_1 -> proceso.setNotaLector1(calificacion);
                        case REVISION_LECTOR_2 -> proceso.setNotaLector2(calificacion);
                        case DEFENSA -> {
                                if (responsableNotaLigeroDTO.getResponsableCalificacion().trim()
                                                .equals(proceso.getPersonaTribunal1().trim())) {
                                        proceso.setNotaTribunal1(calificacion);
                                } else if (responsableNotaLigeroDTO.getResponsableCalificacion().trim()
                                                .equals(proceso.getPersonaTribunal2().trim())) {
                                        proceso.setNotaTribunal2(calificacion);
                                } else {
                                        throw new IllegalArgumentException(
                                                        "La personsa con nombre:"
                                                                        + responsableNotaLigeroDTO
                                                                                        .getResponsableCalificacion()
                                                                        + " no se encontró en el proceso de titulación.");
                                }

                        }
                        default ->
                                throw new IllegalArgumentException("El paso no permite la inserción de calificación.");
                }

                procesoTitulacionRepository.save(proceso);
        }

        @Override
        public void agregarTutorProcesoTitulacion(Integer idProcesoTitulacion, PersonaDTO personatutorDTO) {

                ProcesoTitulacion procesoTitu = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

                Persona persona = this.personaRepository.findById(personatutorDTO.getId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Tutor con id: " + personatutorDTO.getId() + " no encontrado"));

                procesoTitu.setTutorPoyecto(persona.getNombre() + " " + persona.getApellido());

                procesoTitulacionRepository.save(procesoTitu);
        }

        @Override
        public PersonaTitulacionLigeroDTO buscarTutorProcesoTitulacion(Integer idProcesoTitulacion) {

                ProcesoTitulacion procesoTitu = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));
                PersonaTitulacionLigeroDTO tutorLigeroDTO = new PersonaTitulacionLigeroDTO();
                tutorLigeroDTO.setNombre(procesoTitu.getTutorPoyecto());
                tutorLigeroDTO.setIdProceso(procesoTitu.getId());

                return tutorLigeroDTO;

        }

        @Override
        public void agregarPersonaTribunaUno(Integer idProcesoTitulacion, PersonaDTO personaDTO) {

                ProcesoTitulacion procesoTitu = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

                Persona persona = this.personaRepository.findById(personaDTO.getId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Persona Tribunal con id: " + personaDTO.getId() + " no encontrado"));

                procesoTitu.setPersonaTribunal1(persona.getNombre() + " " + persona.getApellido());

                procesoTitulacionRepository.save(procesoTitu);
        }

        @Override
        public void agregarPersonaTribunalDos(Integer idProcesoTitulacion, PersonaDTO personaDTO) {
                // TODO Auto-generated method stub

                ProcesoTitulacion procesoTitu = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

                Persona persona = this.personaRepository.findById(personaDTO.getId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Persona Tribunal con id: " + personaDTO.getId() + " no encontrado"));

                procesoTitu.setPersonaTribunal2(persona.getNombre() + " " + persona.getApellido());

                procesoTitulacionRepository.save(procesoTitu);
        }

        @Override
        public List<PersonaTitulacionLigeroDTO> buscarPersonasTribunal(Integer idProcesoTitulacion) {
                ProcesoTitulacion procesoTitu = procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));
                List<PersonaTitulacionLigeroDTO> personas = List.of(
                                new PersonaTitulacionLigeroDTO(procesoTitu.getId(), procesoTitu.getPersonaTribunal1()),
                                new PersonaTitulacionLigeroDTO(procesoTitu.getId(), procesoTitu.getPersonaTribunal2()));
                if (personas.isEmpty()) {
                        return null;
                }
                return personas;
        }

}
