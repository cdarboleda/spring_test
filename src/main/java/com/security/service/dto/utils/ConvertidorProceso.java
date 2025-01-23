package com.security.service.dto.utils;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.security.db.Proceso;
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

    public ProcesoCompletoDTO convertirACompletoDTO(Proceso proceso) {

        ProcesoCompletoDTO procesoDTO = new ProcesoCompletoDTO();

        // if (proceso.getTipoProceso().equals(TipoProceso.PAGO_DOCENTE)) {
        //     procesoDTO = new ProcesoCompletoPagoDocenteDTO(proceso.getProcesoPagoDocente());
        // } else if (proceso.getTipoProceso().equals(TipoProceso.TITULACION)) {
        //     procesoDTO = new ProcesoCompletoTitulacionDTO(proceso.getProcesoTitulacion());
        // }

        procesoDTO.setId(proceso.getId());
        procesoDTO.setDescripcion(proceso.getDescripcion());
        procesoDTO.setFechaInicio(proceso.getFechaInicio());
        procesoDTO.setFechaFin(proceso.getFechaFin());
        procesoDTO.setFinalizado(proceso.getFinalizado());
        procesoDTO.setTipoProceso(proceso.getTipoProceso().toString());
        procesoDTO.setCarpetasDocumento(
                proceso.getCarpetasDocumento().stream()
                        .map(convertidorCarpetaDocumento::convertirALigeroDTO)
                        .collect(Collectors.toList()));

        procesoDTO.setPasos(
                proceso.getPasos().stream()
                        .map(convertidorPaso::convertirAPasoDTO)
                        .collect(Collectors.toList()));
        procesoDTO.setRequiriente(convertidorPersona.convertirALigeroDTO(proceso.getRequiriente()));

        return procesoDTO;
    }

    public ProcesoDTO convertirALigeroDTO(Proceso proceso) {

        ProcesoDTO procesoDTO = new ProcesoDTO();
        procesoDTO.setId(proceso.getId());
        procesoDTO.setDescripcion(proceso.getDescripcion());
        procesoDTO.setFechaInicio(proceso.getFechaInicio());
        procesoDTO.setFechaFin(proceso.getFechaFin());
        procesoDTO.setFinalizado(proceso.getFinalizado());
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
