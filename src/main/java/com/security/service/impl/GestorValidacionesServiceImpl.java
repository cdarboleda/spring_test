package com.security.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.security.db.Paso;
import com.security.db.enums.Estado;
import com.security.repo.IPasoRepository;
import com.security.service.IGestorValidacionesService;

import com.security.service.dto.PasoValidacionDTO;

@Service
@Transactional
public class GestorValidacionesServiceImpl implements IGestorValidacionesService {

    @Autowired
    private PasoServiceImpl pasoService;

    @Autowired
    private IPasoRepository pasoRepository;

    private static final Boolean ES_VALIDO = true;
    private static final Boolean NO_ES_VALIDO = false;

    @Override
    public PasoValidacionDTO validarAccionPaso(Integer pasoId, Integer personaId) {
        PasoValidacionDTO pasoDTO = new PasoValidacionDTO();
        pasoDTO.setEsValido(NO_ES_VALIDO); // valor por defecto

        if (pasoId == null || personaId == null) {
            pasoDTO.setMensaje("Identificadores inválidos: pasoId o personaId es null.");
            return pasoDTO;
        }

        Optional<Paso> paso;
        try {
            paso = this.pasoRepository.findById(pasoId);
        } catch (Exception e) {
            pasoDTO.setMensaje("Ocurrió un error al buscar el paso: " + e.getMessage());
            return pasoDTO;
        }

        if (paso.isEmpty()) {
            pasoDTO.setMensaje("No se encontró el paso con ID: " + pasoId);
            return pasoDTO;
        }

        Paso pasoEntity = paso.get();

        if (pasoEntity.getResponsable() == null) {
            pasoDTO.setMensaje("El paso no tiene un responsable asignado.");
            return pasoDTO;
        }

        if (!pasoEntity.getResponsable().getId().equals(personaId)) {
            pasoDTO.setMensaje("No está autorizada para este paso.");

            List<Paso> pasos = pasoService.findByProcesoId(pasoEntity.getProceso().getId());
            pasos.stream()
                    .filter(p -> p.getEstado() == Estado.EN_CURSO)
                    .findFirst()
                    .ifPresentOrElse(
                            p -> llenarDatosPasoDTODesdePaso(pasoDTO, p),
                            () -> pasoDTO.setMensaje("No se encontró ningún paso en estado 'EN_CURSO'."));

            return pasoDTO;
        }

        // A esta altura ya sabemos que el usuario es el responsable del paso que
        // consulta.

        if (pasoEntity.getEstado() != Estado.EN_CURSO) {
            pasoDTO.setMensaje("El paso no se encuentra en estado 'EN_CURSO'.");
            return pasoDTO;
        }

        // El usuario es el responsable Y el paso está en curso
        pasoDTO.setEsValido(ES_VALIDO);
        pasoDTO.setMensaje("Autorización correcta.");
        llenarDatosPasoDTODesdePaso(pasoDTO, pasoEntity);

        return pasoDTO;
    }

    private void llenarDatosPasoDTODesdePaso(PasoValidacionDTO dto, Paso paso) {
        if (paso.getResponsable() != null) {
            dto.setResponsableNombreCompleto(
                    paso.getResponsable().getNombre() + " " + paso.getResponsable().getApellido());
            dto.setResponsableCorreo(paso.getResponsable().getCorreo());
            dto.setResponsableTelefono(paso.getResponsable().getTelefono());
        }
        dto.setFechaInicio(paso.getFechaInicio());
        dto.setEstado(paso.getEstado().toString());
        dto.setObservacion(paso.getObservacion());
    }

}
