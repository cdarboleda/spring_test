package com.security.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.ProcesoLog;
import com.security.db.enums.Evento;
import com.security.db.enums.PasoTitulacion;
import com.security.service.IGestorProcesoLogService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoLogService;
import com.security.service.dto.PasoDTO;
import com.security.service.dto.ProcesoLogDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorProcesoLogServiceImpl implements IGestorProcesoLogService {
    @Autowired
    private IProcesoLogService procesoLogService;

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
        } else {

            procesoLog.setResponsableId(paso.getResponsable().getId());
            procesoLog.setResponsableCedula(paso.getResponsable().getCedula());
            procesoLog.setResponsableNombre(
                    paso.getResponsable().getNombre() + " " + paso.getResponsable().getApellido());

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
        List<ProcesoLogDTO> logsOriginales = this.procesoLogService.findByProcesoId(id).stream()
                .sorted(Comparator.comparing(ProcesoLog::getId))
                .filter(log -> !"Pendiente".equalsIgnoreCase(log.getPasoEstadoDescripcion()))
                .map(this::toDto) // Convertimos a DTO
                .collect(Collectors.toList());

        List<ProcesoLogDTO> logsFiltrados = new ArrayList<>();
        boolean pasoInicialGuardado = false;

        for (ProcesoLogDTO log : logsOriginales) {
            if (!pasoInicialGuardado && Evento.CREACION.equals(log.getTipoEvento())
                    && "EN_CURSO".equalsIgnoreCase(log.getPasoEstado())) {
                log.setPasoEstadoDescripcion("INICIO DEL PROCESO");
                logsFiltrados.add(log);
                pasoInicialGuardado = true;
                continue;
            }

            if (!"EN_CURSO".equalsIgnoreCase(log.getPasoEstado())) {
                logsFiltrados.add(log);
            }
        }

        if (!logsOriginales.isEmpty()) {
            ProcesoLogDTO ultimo = logsOriginales.get(logsOriginales.size() - 1);

            if ("EN_CURSO".equalsIgnoreCase(ultimo.getPasoEstado()) &&
                    logsFiltrados.stream().noneMatch(l -> l.getId().equals(ultimo.getId()))) {
                ultimo.setPasoObservacion("");
                logsFiltrados.add(ultimo);
            }
        }

        return formatearTabla(logsFiltrados);
    }

    private List<ProcesoLogDTO> formatearTabla(List<ProcesoLogDTO> listProcesoLog) {
        if (!listProcesoLog.isEmpty()) {
            listProcesoLog.getFirst().setPasoEstadoDescripcion("INICIO DEL PROCESO");
        }

        for (int i = 0; i < listProcesoLog.size(); i++) {
            ProcesoLogDTO actual = listProcesoLog.get(i);

            if ("FINALIZADO".equalsIgnoreCase(actual.getPasoEstado())) {
                for (int j = i + 1; j < listProcesoLog.size(); j++) {
                    ProcesoLogDTO siguiente = listProcesoLog.get(j);

                    if (siguiente.getPasoOrden() == actual.getPasoOrden() + 1) {
                        if (!"FINALIZADO".equalsIgnoreCase(siguiente.getPasoEstado())
                                && !PasoTitulacion.APROBACION_PLAN_TITULACION.toString().equalsIgnoreCase(actual.getPasoNombre())
                                && !PasoTitulacion.DESIGNACION_LECTORES.toString().equalsIgnoreCase(actual.getPasoNombre())
                                && !PasoTitulacion.REVISION_LECTOR_1.toString().equalsIgnoreCase(actual.getPasoNombre())
                                && !PasoTitulacion.REVISION_LECTOR_1.toString().equalsIgnoreCase(actual.getPasoNombre())
                                && !PasoTitulacion.CORRECCION_OBSERVACION_LECTORES.toString().equalsIgnoreCase(actual.getPasoNombre())) {
                            actual.setPasoEstadoDescripcion("Enviado");
                        }
                        break; // Ya encontramos el siguiente paso, salimos
                    }
                }
            }
        }

        return listProcesoLog;
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
        dto.setPasoEstadoDescripcion(entity.getPasoEstadoDescripcion());
        dto.setPasoObservacion(entity.getPasoObservacion());
        dto.setFechaCambio(entity.getFechaCambio());
        return dto;
    }

}
