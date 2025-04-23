package com.security.service.dto.utils;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.security.db.Proceso;
import com.security.db.ProcesoPagoDocente;
import com.security.db.ProcesoTitulacion;
import com.security.db.enums.TipoProceso;
import com.security.service.dto.ProcesoCompletoDTO;
import com.security.service.dto.ProcesoCompletoPagoDocenteDTO;
import com.security.service.dto.ProcesoCompletoTitulacionDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.ProcesoPagoDocenteDTO;

@Component
public class ConvertidorProceso {

    @Autowired
    private ConvertidorCarpetaDocumento convertidorCarpetaDocumento;
    @Autowired
    private ConvertidorPersona convertidorPersona;
    @Autowired
    private ConvertidorPaso convertidorPaso;

    public Object convertirACompletoDTO(Object procesoEspecifico) {

        ProcesoCompletoDTO procesoDTO = null;

        if (procesoEspecifico instanceof ProcesoPagoDocente) {
            ProcesoPagoDocente pagoDocente = (ProcesoPagoDocente) procesoEspecifico;
            procesoDTO = new ProcesoCompletoPagoDocenteDTO(pagoDocente);

        } else if (procesoEspecifico instanceof ProcesoTitulacion) {
            ProcesoTitulacion titulacion = (ProcesoTitulacion) procesoEspecifico;
            procesoDTO = new ProcesoCompletoTitulacionDTO(titulacion);

        } else {
            throw new IllegalArgumentException("Tipo de proceso desconocido");
        }

        // Seteamos los campos comunes
        if (procesoDTO != null) {
            Proceso proceso = procesoEspecifico instanceof ProcesoPagoDocente
                    ? ((ProcesoPagoDocente) procesoEspecifico).getProceso()
                    : ((ProcesoTitulacion) procesoEspecifico).getProceso();

            procesoDTO.setId(proceso.getId());
            procesoDTO.setDescripcion(proceso.getDescripcion());
            procesoDTO.setFechaInicio(proceso.getFechaInicio());
            procesoDTO.setFechaFin(proceso.getFechaFin());
            procesoDTO.setFinalizado(proceso.getFinalizado());
            procesoDTO.setTipoProceso(proceso.getTipoProceso().toString());

            procesoDTO.setCarpetasDocumento(
                    proceso.getCarpetasDocumento() != null ? proceso.getCarpetasDocumento().stream()
                            .map(convertidorCarpetaDocumento::convertirALigeroDTO)
                            .collect(Collectors.toList())
                            : null);

            procesoDTO.setPasos(
                    proceso.getPasos() != null ? proceso.getPasos().stream()
                            .map(convertidorPaso::convertirAPasoDTO)
                            .collect(Collectors.toList())
                            : null);
            procesoDTO.setRequiriente(convertidorPersona.convertirALigeroDTO(proceso.getRequiriente()));

        }

        return procesoDTO;
    }

    // public ProcesoCompletoDTO convertirACompletoDTO(Proceso proceso) {

    // ProcesoCompletoDTO procesoDTO = new ProcesoCompletoDTO();
    // procesoDTO.setId(proceso.getId());
    // procesoDTO.setDescripcion(proceso.getDescripcion());
    // procesoDTO.setFechaInicio(proceso.getFechaInicio());
    // procesoDTO.setFechaFin(proceso.getFechaFin());
    // procesoDTO.setFinalizado(proceso.getFinalizado());
    // procesoDTO.setTipoProceso(proceso.getTipoProceso().toString());

    // procesoDTO.setCarpetasDocumento(
    // proceso.getCarpetasDocumento().stream()
    // .map(convertidorCarpetaDocumento::convertirALigeroDTO)
    // .collect(Collectors.toList()));

    // procesoDTO.setPasos(
    // proceso.getPasos().stream()
    // .map(convertidorPaso::convertirAPasoDTO)
    // .collect(Collectors.toList()));
    // procesoDTO.setRequiriente(convertidorPersona.convertirALigeroDTO(proceso.getRequiriente()));

    // return procesoDTO;
    // }

    public ProcesoDTO convertirALigeroDTO(Proceso proceso) {

        ProcesoDTO procesoDTO = new ProcesoDTO();
        procesoDTO.setId(proceso.getId());
        procesoDTO.setDescripcion(proceso.getDescripcion());
        procesoDTO.setFechaInicio(proceso.getFechaInicio());
        procesoDTO.setFechaFin(proceso.getFechaFin());
        procesoDTO.setFinalizado(proceso.getFinalizado());
        procesoDTO.setCancelado(proceso.getCancelado());
        return procesoDTO;
    }

    public ProcesoPagoDocenteDTO convertirProcesoPagoDocenteALigeroDTO(Proceso proceso) {

        ProcesoPagoDocenteDTO procesoDTO = new ProcesoPagoDocenteDTO();
        procesoDTO.setId(proceso.getId());
        procesoDTO.setDescripcion(proceso.getDescripcion());
        procesoDTO.setFechaInicio(proceso.getFechaInicio());
        // procesoDTO.setModalidadVirtual(proceso.getProcesoPagoDocente().getModalidadVirtual());
        procesoDTO.setFechaFin(proceso.getFechaFin());
        procesoDTO.setFinalizado(proceso.getFinalizado());
        procesoDTO.setTipoProceso(proceso.getTipoProceso().toString());
        return procesoDTO;
    }

}
