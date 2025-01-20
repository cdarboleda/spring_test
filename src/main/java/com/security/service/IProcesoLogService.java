package com.security.service;

import java.util.List;

import com.security.db.ProcesoLog;

public interface IProcesoLogService {

    public ProcesoLog insert(ProcesoLog procesoLog);

    public List<ProcesoLog> findAll();

    public List<ProcesoLog> findByProcesoId(Integer id);

    //public List<ProcesoLog> findByIdProcesoAndIdPaso(Integer idProceso, Integer idPaso);

    
}
