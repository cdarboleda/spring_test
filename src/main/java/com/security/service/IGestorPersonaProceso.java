package com.security.service;

import java.util.List;

import com.security.db.Proceso;

public interface IGestorPersonaProceso {
    public List<Proceso> findProcesosByPersonaId(Integer id);
    public List<Proceso> findProcesosWherePersonaIsOwner(Integer id);
}
