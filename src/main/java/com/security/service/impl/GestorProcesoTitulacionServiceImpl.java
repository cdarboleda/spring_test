package com.security.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.ProcesoTitulacion;
import com.security.db.enums.PasoTitulacion;
import com.security.repo.IPasoRepository;
import com.security.repo.IPersonaRepository;
import com.security.repo.IProcesoTitulacionRepository;
import com.security.service.IGestorProcesoTitulacionService;

import com.security.service.dto.PasoDTO;
import com.security.service.dto.TitulacionResponsableNotaLigeroDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorProcesoTitulacionServiceImpl implements IGestorProcesoTitulacionService {

        @Autowired
        private IPasoRepository pasoRepository;

        @Autowired
        private IPersonaRepository personaRepository;

        @Autowired
        private IProcesoTitulacionRepository procesoTitulacionRepository;

        // obtiene el nombre del responsable de un paso y la nota
        // se usa para obtener el nombre del revisor, tutor, lectores y sus notas
        @Override
        public TitulacionResponsableNotaLigeroDTO buscarResponsableNotaPaso(Integer idProcesoTitulacion,
                        String nombrePaso) {

                // Buscar el paso correspondiente
                Paso pasoEspecifico = this.pasoRepository.findByProcesoId(idProcesoTitulacion).stream()
                                .filter(paso -> paso.getNombre().equals(nombrePaso))
                                .findFirst()
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Paso no encontrado con nombre: " + nombrePaso));

                // Buscar la persona responsable
                Persona persona = Optional.ofNullable(pasoEspecifico.getResponsable())
                                .flatMap(responsable -> personaRepository.findById(responsable.getId()))
                                .orElse(null); // Si la persona no existe dejamos el nombre vacio

                String observacion = Optional.ofNullable(pasoEspecifico.getObservacion()).orElse(null);

                // Crear el DTO y asignar el nombre del responsable si existe y las
                // observaciones
                TitulacionResponsableNotaLigeroDTO revisorNotaLigeroDTO = new TitulacionResponsableNotaLigeroDTO();
                revisorNotaLigeroDTO.setResponsableCalificacion(
                                persona != null ? formatNombreCompleto(persona) : "No asignado");
                revisorNotaLigeroDTO.setObservacion(
                                observacion == null ? "No se ha registrado observaciones" : observacion);

                // Convertir nombrePaso en un enum
                PasoTitulacion pasoTitulacion = Optional.ofNullable(PasoTitulacion.fromString(nombrePaso))
                                .orElseThrow(() -> new IllegalArgumentException("Paso inválido: " + nombrePaso));
                // Obtener la nota del paso específico
                Double nota = this.procesoTitulacionRepository.findById(idProcesoTitulacion)
                                .map(proceso -> switch (pasoTitulacion) {
                                        //case REVISION_IDONEIDAD -> proceso.getNotaPropuestaProyecto();
                                        case REVISION_LECTOR_1 -> proceso.getNotaLector1();
                                        case REVISION_LECTOR_2 -> proceso.getNotaLector2();
                                        default -> null; // null si el paso no tienecalificacion
                                })
                                .orElse(null);

                revisorNotaLigeroDTO.setCalificacionPaso(nota);

                return revisorNotaLigeroDTO;
        }

        private String formatNombreCompleto(Persona persona) {
                return String.format("%s %s",
                                Objects.toString(persona.getNombre(), ""),
                                Objects.toString(persona.getApellido(), ""))
                                .trim();
        }

}
