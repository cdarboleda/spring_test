package com.security.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.ProcesoTitulacion;
import com.security.service.dto.utils.ConvertidorFechaFormato;

@Component
public class EmailTemplateBuilder {

    public Map<String, Object> construirVariablesPaso(Paso paso) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("procesoId", paso.getProceso().getId());
        vars.put("pasoActual", paso.getNombre());
        return vars;
    }

    public Map<String, Object> construirVariablesProceso(ProcesoTitulacion proceso, Persona req, String plantilla) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("procesoId", proceso.getId());
        vars.put("requirienteNombre", req.getNombre());
        vars.put("requirienteApellido", req.getApellido());
        if ("ProcesoCancelado".equals(plantilla)) {
            // vars.put("fecha",
            // ConvertidorFechaFormato.formatearFecha(proceso.getProceso().getFechaFin()));
            // vars.put("motivo", proceso.getMotivoCancelacion());
        } else if ("ProcesoIniciado".equals(plantilla)) {
            vars.put("fecha", ConvertidorFechaFormato.formatearFecha(proceso.getProceso().getFechaInicio()));
        } else if ("ProcesoFinalizado".equals(plantilla)) {
            vars.put("fecha", ConvertidorFechaFormato.formatearFecha(proceso.getProceso().getFechaFin()));

        }
        return vars;
    }
}
