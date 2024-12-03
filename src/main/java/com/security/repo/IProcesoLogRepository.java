package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.ProcesoLog;

public interface IProcesoLogRepository extends JpaRepository<ProcesoLog, Integer>{

    public List<ProcesoLog> findByIdProceso(Integer id);

    public List<ProcesoLog> findByIdProcesoAndIdProcesoPaso(Integer idProceso, Integer idProcesoPaso);
    
}
