package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.db.ProcesoTitulacion;

import jakarta.transaction.Transactional;

public interface IProcesoTitulacionRepository extends JpaRepository<ProcesoTitulacion, Integer>{
    @Transactional
    @Query("DELETE FROM ProcesoTitulacion pt WHERE pt.id = :procesoId")
    @Modifying
    void deleteById(@Param("procesoId") Integer procesoId);
    
}
