package com.security.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.ProcesoLog;
import com.security.db.enums.Estado;
import com.security.db.enums.Evento;
import com.security.db.enums.PasoTitulacion;
import com.security.service.IGestorProcesoLogService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoLogService;

import com.security.service.dto.ProcesoLogDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorProcesoLogServiceImpl implements IGestorProcesoLogService {
    @Autowired
    private IProcesoLogService procesoLogService;

    @Autowired
    private PasoServiceImpl pasoService;
    @Autowired
    private IPersonaService personaService;

    @Override
    public ProcesoLog insertarProcesoLog(ProcesoLog procesoLog) {
        if (procesoLog.getResponsableId() == null) {// si en el metodo padre noseteo un responsable
            Persona responsable = personaService.findById(procesoLog.getResponsableId());
            procesoLog.setResponsableId(responsable.getId());
            procesoLog.setResponsableCedula(responsable.getCedula());
            procesoLog.setResponsableNombre(responsable.getNombre() + " " + responsable.getApellido());
        }
        try {
            return this.procesoLogService.insert(procesoLog);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar log de paso " + procesoLog.getPasoOrden() + ": "
                    + procesoLog.getPasoNombre() + ", del procesoId: " + procesoLog.getProcesoId());
        }
    }

    @Override
    public ProcesoLog insertarProcesoLog(Paso paso, Evento tipoEvento) {
        ProcesoLog procesoLog = new ProcesoLog();
        if (paso.getResponsable() == null) {
            procesoLog.setResponsableId(null);
            procesoLog.setResponsableCedula(null);
            procesoLog.setResponsableNombre(null);
            procesoLog.setResponsableRol(null);
        } else {

            procesoLog.setResponsableId(paso.getResponsable().getId());
            procesoLog.setResponsableCedula(paso.getResponsable().getCedula());
            procesoLog.setResponsableNombre(
                    paso.getResponsable().getNombre() + " " + paso.getResponsable().getApellido());
            procesoLog.setResponsableRol(paso.getRol().getNombre());

        }

        procesoLog.setProcesoId(paso.getProceso().getId());

        // procesoLog.setPasoId(paso.getId());
        // procesoLog.setPasoId(paso.getId()==null?paso.getId():null);
        procesoLog.setPasoEstado(paso.getEstado().toString());
        procesoLog.setPasoEstadoDescripcion(paso.getDescripcionEstado());
        procesoLog.setPasoNombre(paso.getNombre());
        procesoLog.setPasoOrden(paso.getOrden());
        procesoLog.setTipoEvento(tipoEvento);
        procesoLog.setPasoObservacion(paso.getObservacion());

        try {

            return this.procesoLogService.insert(procesoLog);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar log de paso " + procesoLog.getPasoOrden() + ": "
                    + procesoLog.getPasoNombre() + ", del procesoId: " + procesoLog.getProcesoId());
        }
    }

    @Override
    public List<ProcesoLogDTO> buscarLogProcesoFiltradoTitulacion(Integer id) {
        // 1. Obtener logs válidos y convertirlos a DTO
        List<ProcesoLogDTO> logsFiltrados = this.procesoLogService.findByProcesoId(id).stream()
                .sorted(Comparator.comparing(ProcesoLog::getId))
                .filter(log -> !"Pendiente".equalsIgnoreCase(log.getPasoEstadoDescripcion()))
                .map(this::toDto)
                .filter(log -> Evento.CREACION.equals(log.getTipoEvento())
                        && "EN_CURSO".equalsIgnoreCase(log.getPasoEstado())
                        || !"EN_CURSO".equalsIgnoreCase(log.getPasoEstado()))
                .collect(Collectors.toCollection(ArrayList::new));

        // Agregamos pasos en curso como logs adicionales
        this.pasoService.findByProcesoId(id).stream()
                .filter(p -> Estado.EN_CURSO.equals(p.getEstado()))
                .map(paso -> {
                    ProcesoLogDTO dto = new ProcesoLogDTO();
                    dto.setId(paso.getId());
                    dto.setResponsableId(paso.getResponsable().getId());
                    dto.setResponsableNombre(
                            paso.getResponsable().getNombre() + " " + paso.getResponsable().getApellido());
                    dto.setResponsableRol(paso.getRol().getNombre());
                    dto.setPasoOrden(paso.getOrden());
                    dto.setPasoNombre(paso.getNombre());
                    dto.setPasoEstado(paso.getEstado().toString());
                    dto.setTipoEvento(Evento.ESTADO);
                    dto.setPasoEstadoDescripcion(paso.getDescripcionEstado());
                    dto.setFechaCambio(paso.getFechaInicio());
                    return dto;
                })
                .forEach(logsFiltrados::add);

        return formatearLogs(logsFiltrados);
    }

    private List<ProcesoLogDTO> formatearLogs(List<ProcesoLogDTO> logs) {
        if (logs.isEmpty())
            return logs;

        logs.getFirst().setPasoEstadoDescripcion("INICIO DEL PROCESO");

        Set<String> pasosExcluidos = Set.of(
                PasoTitulacion.APROBACION_PLAN_TITULACION.toString(),
                PasoTitulacion.DESIGNACION_LECTORES.toString(),
                PasoTitulacion.REVISION_LECTOR_1.toString(),
                PasoTitulacion.CORRECCION_OBSERVACION_LECTORES.toString());

        for (int i = 0; i < logs.size() - 1; i++) {
            ProcesoLogDTO actual = logs.get(i);

            if ("FINALIZADO".equalsIgnoreCase(actual.getPasoEstado())) {
                ProcesoLogDTO siguiente = logs.get(i + 1);
                if (siguiente.getPasoOrden() == actual.getPasoOrden() + 1 &&
                        !"FINALIZADO".equalsIgnoreCase(siguiente.getPasoEstado()) &&
                        !pasosExcluidos.contains(actual.getPasoNombre())) {
                    actual.setPasoEstadoDescripcion("Enviado");
                }
            }
        }

        return logs;
    }

    private ProcesoLogDTO toDto(ProcesoLog entity) {
        ProcesoLogDTO dto = new ProcesoLogDTO();
        dto.setId(entity.getId());
        dto.setResponsableId(entity.getResponsableId());
        dto.setResponsableNombre(entity.getResponsableNombre());
        dto.setPasoOrden(entity.getPasoOrden());
        dto.setPasoNombre(entity.getPasoNombre());
        dto.setPasoEstado(entity.getPasoEstado());
        dto.setTipoEvento(entity.getTipoEvento());
        dto.setResponsableRol(entity.getResponsableRol());
        dto.setPasoEstadoDescripcion(entity.getPasoEstadoDescripcion());
        dto.setPasoObservacion(entity.getPasoObservacion());
        dto.setFechaCambio(entity.getFechaCambio());
        return dto;
    }

}
