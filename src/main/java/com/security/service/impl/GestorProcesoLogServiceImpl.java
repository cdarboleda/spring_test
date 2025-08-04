package com.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.ProcesoLog;
import com.security.db.enums.Evento;
import com.security.service.IGestorProcesoLogService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoLogService;

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

        ///////////////////////////////////////////////////
        procesoLog.setObservacion(paso.getObservacion());

        try {
            ProcesoLog proclog = this.procesoLogService.insert(procesoLog);
            System.out.println("-------------------------------------------------------");
            System.out.println(proclog.toString());
            return proclog;

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar log de paso " + procesoLog.getPasoOrden() + ": "
                    + procesoLog.getPasoNombre() + ", del procesoId: " + procesoLog.getProcesoId());
        }
    }

}
