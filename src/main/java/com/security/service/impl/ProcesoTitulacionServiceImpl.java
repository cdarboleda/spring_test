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
import com.security.service.IGestorPasoService;
import com.security.service.IGestorPersonaService;
import com.security.service.IGestorProcesoService;
import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.PasoDTO;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.TitulacionResponsableNotaLigeroDTO;
import com.security.service.dto.PersonaTitulacionLigeroDTO;
import com.security.service.dto.ProcesoCompletoTitulacionDTO;
import com.security.service.dto.ProcesoTitulacionDTO;
import com.security.service.dto.ProcesoTitulacionLigeroDTO;

@Service
@Transactional
public class ProcesoTitulacionServiceImpl implements IProcesoTitulacionService {

        @Autowired
        private IProcesoTitulacionRepository procesoTitulacionRepository;

        @Autowired
        private IPersonaRepository personaRepository;

        @Autowired
        private IGestorProcesoService gestorProcesoService;

        @Autowired
        private IGestorPasoService gestorPasoService;

        @Autowired
        private IGestorPersonaService gestorPersonaService;

        private final static String ROL_SECRETARIA = "secretaria";

        @Override
        public Object insertarProcesoTitulacion(ProcesoTitulacionDTO procesoTitulacionDTO) {
                // Insertar el proceso
                ProcesoCompletoTitulacionDTO proceso = (ProcesoCompletoTitulacionDTO) gestorProcesoService
                                .insert(procesoTitulacionDTO);

                // Retornar el proceso creado, si es necesario
                return proceso;
        }

        @Transactional
        public void asignarSecretariaAlproceso(ProcesoCompletoTitulacionDTO proceso) {
                // Verificar que el proceso tiene pasos
                if (proceso.getPasos() == null || proceso.getPasos().isEmpty()) {
                        throw new IllegalStateException("El proceso no tiene pasos definidos.");
                }

                // Paso con orden 2
                PasoDTO paso2 = proceso.getPasos().stream()
                                .filter(p -> p.getOrden() == 2)
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("No se encontró el paso con orden 2."));

                // Obtener secretaria activa
                Persona secretaria = gestorPersonaService.findPersonasByRol(ROL_SECRETARIA).stream()
                                .filter(Persona::getActivo)
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("No hay secretaria activa."));

                // Actualizar responsable
                this.gestorPasoService.updatePasoResponsable(paso2.getId(), secretaria.getId());

                System.out.println("Paso actualizado con responsable: " + secretaria.getNombre() + " al paso "
                                + paso2.getId());

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

                                //hacer a lguna comprobacion parq no inserte null al inicio
                procesoExistente.setFechaDefensa(procesoTitulacionDTO.getFechaDefensa());

                // Optional.ofNullable(procesoTitulacionDTO.getNotaPropuestaProyecto())
                // .ifPresent(procesoExistente::setNotaPropuestaProyecto);
                Optional.ofNullable(procesoTitulacionDTO.getNotaLector1()).ifPresent(procesoExistente::setNotaLector1);
                Optional.ofNullable(procesoTitulacionDTO.getNotaLector2()).ifPresent(procesoExistente::setNotaLector2);
                Optional.ofNullable(procesoTitulacionDTO.getTutorId()).ifPresent(procesoExistente::setTutorId);
                Optional.ofNullable(procesoTitulacionDTO.getGrupo()).ifPresent(procesoExistente::setGrupo);

                Optional.ofNullable(procesoTitulacionDTO.getNotaTribunal1())
                                .ifPresent(procesoExistente::setNotaTribunal1);
                Optional.ofNullable(procesoTitulacionDTO.getPersonaTribunal1())
                                .ifPresent(procesoExistente::setPersonaTribunal1);
                Optional.ofNullable(procesoTitulacionDTO.getNotaTribunal2())
                                .ifPresent(procesoExistente::setNotaTribunal2);
                Optional.ofNullable(procesoTitulacionDTO.getPersonaTribunal2())
                                .ifPresent(procesoExistente::setPersonaTribunal2);
                Optional.ofNullable(procesoTitulacionDTO.getNotaTribunal3())
                                .ifPresent(procesoExistente::setNotaTribunal3);

                // Guardar cambios
                procesoTitulacionRepository.save(procesoExistente);

                ConvertidorProcesoTitulacion convertidor = new ConvertidorProcesoTitulacion();
                ProcesoTitulacionLigeroDTO procesoDTO = convertidor.convertirProcesoTitulacion(procesoExistente);
                return procesoDTO;
        }

        // PUEDE SE Q YA NO HAGA FALTA ESTE METODO
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

                procesoTitu.setTutorId(persona.getId());

                procesoTitulacionRepository.save(procesoTitu);
        }

        @Override
        public PersonaTitulacionLigeroDTO buscarTutorProcesoTitulacion(Integer idProcesoTitulacion) {

                ProcesoTitulacion procesoTitu = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

                Persona persona = this.personaRepository.findById(procesoTitu.getTutorId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "No existe un tutor en el proceso de titulación"));

                PersonaTitulacionLigeroDTO tutorLigeroDTO = new PersonaTitulacionLigeroDTO();

                tutorLigeroDTO.setId(procesoTitu.getTutorId());
                tutorLigeroDTO.setNombre(persona.getNombre() + " " + persona.getApellido());
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
