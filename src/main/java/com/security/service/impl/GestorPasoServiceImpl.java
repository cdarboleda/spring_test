package com.security.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.enums.Estado;
import com.security.db.enums.EstadoHelper;
import com.security.db.enums.Evento;
import com.security.exception.CustomException;
import com.security.factory.PasoFactoryManager;
import com.security.repo.IPasoRepository;
import com.security.service.IGestorPasoService;
import com.security.service.IGestorProcesoLogService;
import com.security.service.IPasoService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoService;
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
    private IGestorProcesoLogService gestorProcesoLogService;

    @Autowired
    private PasoFactoryManager factoryManager;

    @Override
    public List<PasoDTO> crearPasos(String proceso) {
        return this.factoryManager.generarPasosPorProceso(proceso);
    }
    
    @Override
    public PasoDTO updatePasoResponsable(Integer idPaso, Integer idResponsable) {
        Paso paso = this.pasoService.findById(idPaso);
        Persona responsable = this.personaService.findById(idResponsable);

        // if(responsable.activo!=null){
        //     paso.setResponsable(responsable);
        // }
        //el filtro de rol de un responsable le hacemos en el front
        if(responsable.getRoles().contains(paso.getRol())){
            paso.setResponsable(responsable);
            //agregar procesoLog TODO
            this.gestorProcesoLogService.insertarProcesoLog(paso, Evento.RESPONSABLE);
        }else{
            throw new RuntimeException("El responsable no tiene el rol");
        }
        return convertidorPaso.convertirAPasoDTO(paso);

    }

    
    @Override
    public Paso updatePaso(Integer idPaso, PasoDTO pasoDTO) {
        Paso paso = this.pasoService.findById(idPaso);

        paso.setEstado(Estado.valueOf(pasoDTO.getEstado()));

        if (pasoDTO.getObservacion() != null)
            paso.setObservacion(pasoDTO.getObservacion());
        paso.setDescripcionEstado(pasoDTO.getDescripcionEstado() == null
                ? EstadoHelper.getDescripcionPorIndice(Estado.valueOf(pasoDTO.getEstado()), 0)
                : pasoDTO.getDescripcionEstado());

        if (pasoDTO.getEstado().equalsIgnoreCase("FINALIZADO")) {
            paso.setFechaFin(LocalDateTime.now());
        } else if (pasoDTO.getEstado().equalsIgnoreCase("EN_CURSO")) {
            paso.setFechaInicio(LocalDateTime.now());
            paso.setFechaFin(null);
        } else if (pasoDTO.getEstado().equalsIgnoreCase("PENDIENTE")) {
            paso.setFechaInicio(null);
            paso.setFechaFin(null);
        }

        this.gestorProcesoLogService.insertarProcesoLog(paso, Evento.ESTADO);
        return paso; // Guarda los cambios
    }

    @Override
    public List<PasoDTO> findPasosDTOByProcesoId(Integer procesoId){
        return this.pasoRepository.findPasosDTOByProcesoId(procesoId);
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

    


}
