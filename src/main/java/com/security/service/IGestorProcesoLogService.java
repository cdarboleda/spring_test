package com.security.service;

import java.util.List;

import com.security.db.Paso;
import com.security.db.ProcesoLog;
import com.security.db.enums.Evento;
import com.security.service.dto.ProcesoLogDTO;

public interface IGestorProcesoLogService {

    public ProcesoLog insertarProcesoLog(ProcesoLog procesoLog);

    // si no quiero hacer el seteo en los padre deberia delegar esa tarea
    public ProcesoLog insertarProcesoLog(Paso paso, Evento tipoEvento);

    public List<ProcesoLogDTO> buscarLogProcesoFiltradoTitulacion(Integer id);

}
