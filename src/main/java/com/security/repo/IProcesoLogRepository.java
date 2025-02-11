package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.security.db.ProcesoLog;

public interface IProcesoLogRepository extends JpaRepository<ProcesoLog, Integer>{

    @Query("SELECT l FROM ProcesoLog l WHERE l.procesoId = :procesoId")
    public List<ProcesoLog> findByProcesoId(@Param("procesoId") Integer procesoId);    

    @Transactional
    @Modifying
    @Query("DELETE FROM ProcesoLog l WHERE l.procesoId = :procesoId")
    void deleteByProcesoId(@Param("procesoId") Integer procesoId);    
}
