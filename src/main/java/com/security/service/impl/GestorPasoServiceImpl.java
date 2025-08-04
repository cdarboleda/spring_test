package com.security.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Proceso;
// import com.security.db.Rol;
import com.security.db.enums.Estado;
import com.security.db.enums.EstadoHelper;
import com.security.db.enums.Evento;
import com.security.db.enums.Rol;
import com.security.exception.CustomException;
import com.security.factory.PasoFactoryManager;
import com.security.repo.IPasoRepository;
import com.security.service.IGestorCarpetaDocumento;
import com.security.service.IGestorPasoService;
import com.security.service.IGestorProcesoLogService;
import com.security.service.IPasoService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoService;
import com.security.service.dto.CarpetaDocumentoDTO;
import com.security.service.dto.PasoDTO;
import com.security.service.dto.utils.ConvertidorPaso;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorPasoServiceImpl implements IGestorPasoService {

    @Autowired
    private IPasoRepository pasoRepository;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IProcesoService procesoService;

    @Autowired
    private ConvertidorPaso convertidorPaso;

    @Autowired
    private IPasoService pasoService;

    @Autowired
    private EmailPasoRechazado emailPasoRechazado;

    @Autowired
    private IGestorProcesoLogService gestorProcesoLogService;

    @Autowired
    private PasoFactoryManager factoryManager;

    @Autowired
    private IGestorCarpetaDocumento carpetaDocumento;


    @Override
    public List<PasoDTO> crearPasos(String proceso) {
        return this.factoryManager.generarPasosPorProceso(proceso);
    }

    @Override
    public PasoDTO updatePasoResponsable(Integer idPaso, Integer idResponsable) {
        Paso paso = this.pasoService.findById(idPaso);
        Persona responsable = this.personaService.findById(idResponsable);

        if (responsable.getRoles().contains(paso.getRol())) {
            paso.setResponsable(responsable);
            this.gestorProcesoLogService.insertarProcesoLog(paso, Evento.RESPONSABLE);
        } else {
            throw new RuntimeException("El responsable no tiene el rol");
        }
        return convertidorPaso.convertirAPasoDTO(paso);

    }

    @Override
    public List<PasoDTO> updatePasosMismoResponsable(List<Integer> pasosIds, Integer idResponsable, String rol) {
        Persona responsable = this.personaService.findById(idResponsable);

        // Validar si el responsable tiene el rol
        if (!responsable.getRoles().stream().map(Rol::getNombre).toList().contains(rol)) {
            throw new RuntimeException("El responsable no tiene el rol");
        }

        return pasosIds.stream()
                .map(pasoId -> {
                    Paso paso = this.pasoService.findById(pasoId);
                    paso.setResponsable(responsable);
                    this.gestorProcesoLogService.insertarProcesoLog(paso, Evento.RESPONSABLE);
                    return convertidorPaso.convertirAPasoDTO(paso);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Paso updatePaso(Integer idPaso, PasoDTO pasoDTO) {
        Paso paso = this.pasoService.findById(idPaso);

        paso.setEstado(Estado.valueOf(pasoDTO.getEstado()));

        // if (pasoDTO.getObservacion() != null)
        paso.setObservacion(pasoDTO.getObservacion());

        paso.setDescripcionEstado(pasoDTO.getDescripcionEstado() == null
                ? EstadoHelper.getDescripcionPorIndice(Estado.valueOf(pasoDTO.getEstado()), 0)
                : pasoDTO.getDescripcionEstado());

        if (pasoDTO.getEstado().equalsIgnoreCase("FINALIZADO")) {
            paso.setFechaFin(Instant.now());
        } else if (pasoDTO.getEstado().equalsIgnoreCase("EN_CURSO")) {
            paso.setFechaInicio(Instant.now());
            paso.setFechaFin(null);
        } else if (pasoDTO.getEstado().equalsIgnoreCase("PENDIENTE")) {
            paso.setFechaInicio(null);
            paso.setFechaFin(null);
        }

        this.gestorProcesoLogService.insertarProcesoLog(paso,Evento.ESTADO);
        return paso; // Guarda los cambios
    }

    @Override
    public List<PasoDTO> findPasosDTOByProcesoId(Integer procesoId) {
        return this.pasoRepository.findPasosDTOByProcesoId(procesoId);
    }

@Override
public Map<String, Object> avanzarPaso(Integer pasoActualId, Integer pasoSiguienteId, CarpetaDocumentoDTO documentoDTO) {
   
    Map<String, Object> respuesta = new HashMap<>();
    List<PasoDTO> pasosModificados = new ArrayList<>();

    try {
        if (documentoDTO.getUrl() != null) {
            carpetaDocumento.insertarActualizar(documentoDTO);
        }

        PasoDTO pasoActualDTO = new PasoDTO();
        pasoActualDTO.setEstado(Estado.FINALIZADO.toString());
        pasoActualDTO.setDescripcionEstado(EstadoHelper.getDescripcionPorIndice(Estado.FINALIZADO, 0));

        Paso pasoActual = this.updatePaso(pasoActualId, pasoActualDTO);
        pasosModificados.add(convertidorPaso.convertirAPasoDTO(pasoActual));

        if (pasoSiguienteId >0) {
            PasoDTO pasoSiguienteDTO = new PasoDTO();
            pasoSiguienteDTO.setEstado(Estado.EN_CURSO.toString());
            pasoSiguienteDTO.setDescripcionEstado(EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO, 0));

            Paso pasoSiguiente = this.updatePaso(pasoSiguienteId, pasoSiguienteDTO);
            pasosModificados.add(convertidorPaso.convertirAPasoDTO(pasoSiguiente));
        } else {
            Paso paso = this.pasoService.findById(pasoActualId);
            Proceso proceso = this.procesoService.findById(paso.getProceso().getId());
            proceso.setFechaFin(Instant.now());
            proceso.setFinalizado(true);
            respuesta.put("procesoFechaFin", proceso.getFechaFin());
        }

        respuesta.put("pasos", pasosModificados);
        return respuesta;

    } catch (Exception e) {
        throw new RuntimeException("No se pudo actualizar los pasos: Siguiente", e);
    }
}

    // inserta un paso con responsable y proceso
    // el orden no verifica
    @Override
    public Paso insertarUnico(PasoDTO pasoDTO) {
        Paso paso = new Paso();
        if (this.pasoService.findByIdOptional(pasoDTO.getId()).isPresent()) {
            throw new CustomException("Ya existe un paso con ese id: " + pasoDTO.getId(),
                    HttpStatus.BAD_REQUEST);
        }

        convertidorPaso.convertirAEntidad(paso, pasoDTO);
        Persona persona = this.personaService.findById(pasoDTO.getIdResponsable());
        Proceso proceso = this.procesoService.findById(pasoDTO.getIdProceso());
        paso.setProceso(proceso);
        paso.setResponsable(persona);
        return this.pasoRepository.save(paso);
    }

    @Override
    public List<Paso> rechazarPaso(Integer idPasoActual, Integer idPasoAnterior, Map<String, Object> observacionesDTO) {

        PasoDTO pasoActualDTO = new PasoDTO();
        pasoActualDTO.setEstado(Estado.PENDIENTE.toString());
        pasoActualDTO.setDescripcionEstado(EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE,
        1));// este
        // pasoActualDTO.setDescripcionEstado("Rechazado");// o este

        List<Paso> pasos = new ArrayList<>();
        Paso pasoActual = this.updatePaso(idPasoActual, pasoActualDTO);
        pasos.add(pasoActual);

        if (idPasoAnterior != null) {
            PasoDTO pasoAnteriorDTO = new PasoDTO();
            pasoAnteriorDTO.setEstado(Estado.EN_CURSO.toString());
            pasoAnteriorDTO.setDescripcionEstado("En correcciones");// o este
        //     pasoAnteriorDTO.setDescripcionEstado(EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO,
        // 2));// este
            String observaciones = observacionesDTO.get("observaciones").toString();
            pasoAnteriorDTO.setObservacion(observaciones);

            if (observaciones.trim().isEmpty()) {
                pasoAnteriorDTO.setObservacion(null);
            }

            Paso pasoAnterior = this.updatePaso(idPasoAnterior, pasoAnteriorDTO);

            pasos.add(pasoAnterior);

        }

        return pasos;

    }

}
