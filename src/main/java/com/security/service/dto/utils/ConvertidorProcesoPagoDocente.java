package com.security.service.dto.utils;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.security.db.Proceso;
import com.security.db.ProcesoPagoDocente;
import com.security.db.ProcesoTitulacion;
import com.security.service.dto.ProcesoCompletoDTO;
import com.security.service.dto.ProcesoCompletoPagoDocenteDTO;
import com.security.service.dto.ProcesoCompletoTitulacionDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.ProcesoPagoDocenteDTO;

public class ConvertidorProcesoPagoDocente {

    @Autowired
    private ConvertidorCarpetaDocumento convertidorCarpetaDocumento;
    @Autowired
    private ConvertidorPersona convertidorPersona;
    @Autowired
    private ConvertidorPaso convertidorPaso;

    // public Object convertirACompletoDTO(ProcesoPagoDocente proceso) {

    //     ProcesoPagoDocenteDTO procesoDTO = null;

    //     // Seteamos los campos comunes
    //     if (procesoDTO != null) {

    //         procesoDTO.setId(proceso.getId());

            
    //         procesoDTO.setDescripcion(proceso.getDescripcion());
    //         procesoDTO.setFechaInicio(proceso.getFechaInicio());
    //         procesoDTO.setFechaFin(proceso.getFechaFin());
    //         procesoDTO.setFinalizado(proceso.getFinalizado());
    //         //TODO: el de cancelado
    //         procesoDTO.setTipoProceso(procesoEspecifico.getTipoProceso().toString());

    //         procesoDTO.setCarpetasDocumento(
    //                 proceso.getCarpetasDocumento()!=null?
    //                 proceso.getCarpetasDocumento().stream()
    //                         .map(convertidorCarpetaDocumento::convertirALigeroDTO)
    //                         .collect(Collectors.toList())
    //                         :null);

    //         procesoDTO.setPasos(
    //                 proceso.getPasos()!=null?
    //                 proceso.getPasos().stream()
    //                         .map(convertidorPaso::convertirAPasoDTO)
    //                         .collect(Collectors.toList())
    //                         :null);
    //         procesoDTO.setRequiriente(convertidorPersona.convertirALigeroDTO(proceso.getRequiriente()));

    //     }

    //     return procesoDTO;
    // }

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
