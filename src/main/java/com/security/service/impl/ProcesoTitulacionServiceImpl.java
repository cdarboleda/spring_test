package com.security.service.impl;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.security.service.dto.utils.ConvertidorProcesoTitulacion;
import com.security.db.Persona;
import com.security.db.ProcesoTitulacion;
import com.security.repo.IPersonaRepository;
import com.security.repo.IProcesoTitulacionRepository;
import com.security.service.IGestorPasoService;
import com.security.service.IGestorPersonaService;
import com.security.service.IGestorProcesoService;
import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.PasoDTO;
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
        public ProcesoTitulacionLigeroDTO obtenerProcesoTitulacion(Integer id) {
                ConvertidorProcesoTitulacion convertidor = new ConvertidorProcesoTitulacion();
                return convertidor.convertirProcesoTitulacion(this.procesoTitulacionRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado")));
        }

        @Override
        public Object insertarProcesoTitulacion(ProcesoTitulacionDTO procesoTitulacionDTO) {
                // Insertar el proceso
                ProcesoCompletoTitulacionDTO proceso = (ProcesoCompletoTitulacionDTO) gestorProcesoService
                                .insert(procesoTitulacionDTO);

                // Retornar el proceso creado, si es necesario
                return proceso;
        }

        @Override
        public ProcesoTitulacionLigeroDTO actualizarProcesoTitulacion(Integer idProcesoTitulacion,
                        ProcesoTitulacionLigeroDTO procesoTitulacionDTO) {
                ProcesoTitulacion procesoExistente = procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

                Optional.ofNullable(procesoTitulacionDTO.getCalificacionFinal())
                                .ifPresent(procesoExistente::setCalificacionFinal);

                // hacer a lguna comprobacion parq no inserte null al inicio
                // procesoExistente.setFechaDefensa(procesoTitulacionDTO.getFechaDefensa());
                Optional.ofNullable(procesoTitulacionDTO.getFechaDefensa())
                                .ifPresent(procesoExistente::setFechaDefensa);

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
                Optional.ofNullable(procesoTitulacionDTO.getPersonaTribunal3())
                                .ifPresent(procesoExistente::setPersonaTribunal3);

                // Guardar cambios
                procesoTitulacionRepository.save(procesoExistente);

                ConvertidorProcesoTitulacion convertidor = new ConvertidorProcesoTitulacion();
                ProcesoTitulacionLigeroDTO procesoDTO = convertidor.convertirProcesoTitulacion(procesoExistente);
                return procesoDTO;
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
        public PersonaTitulacionLigeroDTO buscarTutorProcesoTitulacion(Integer idProcesoTitulacion) {

                ProcesoTitulacion procesoTitu = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .orElseThrow(() -> new EntityNotFoundException("Proceso de titulación no encontrado"));

                Persona persona = this.personaRepository.findById(procesoTitu.getTutorId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "No existe un tutor en el proceso de titulación"));

                PersonaTitulacionLigeroDTO tutor = new PersonaTitulacionLigeroDTO();

                tutor.setId(procesoTitu.getTutorId());
                tutor.setNombre(persona.getNombre() + " " + persona.getApellido());
                tutor.setIdProceso(procesoTitu.getId());

                return tutor;

        }

        @Override
        public Integer obtenerCantidadProcesosTitulacionTutor(Integer idPersona) {
                if (idPersona == null || idPersona <= 0) {
                        throw new IllegalArgumentException("El ID de la persona no puede ser nulo o menor a cero.");
                }

                try {
                        return this.procesoTitulacionRepository.contarProcesosActivosPorTutor(idPersona);
                } catch (Exception ex) {
                        // Puedes usar un logger aquí en lugar de imprimir si tienes logging configurado
                        System.err.println(
                                        "Error al obtener la cantidad de procesos de titulación: " + ex.getMessage());
                        throw new RuntimeException("No se pudo obtener la cantidad de procesos de titulación.", ex);
                }
        }

}
