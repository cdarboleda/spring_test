package com.security.service;

import com.security.db.Paso;
import com.security.db.ProcesoLog;
import com.security.db.enums.Evento;


public interface IGestorProcesoLogService {

    public ProcesoLog insertarProcesoLog(ProcesoLog procesoLog);
    //si no quiero hacer el seteo en los padre deberia delegar esa tarea
    public ProcesoLog insertarProcesoLog(Paso paso, Evento tipoEvento);

}
