package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.ProcesoLog;

public interface IProcesoLogRepository extends JpaRepository<ProcesoLog, Integer>{

    public List<ProcesoLog> findByProcesoId(Integer id);

    // public List<ProcesoLog> findByIdProcesoAndIdPaso(Integer idProceso, Integer idPaso);
    
}
