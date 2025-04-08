package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.db.ProcesoTitulacion;


import jakarta.transaction.Transactional;

public interface IProcesoTitulacionRepository extends JpaRepository<ProcesoTitulacion, Integer> {

    @Transactional
    @Query("SELECT COUNT(pt) > 0 FROM ProcesoTitulacion pt JOIN Persona p ON p.id = :personaId WHERE pt.id = :procesoId")
    Boolean existsByIdAndPersonaId(@Param("procesoId") Integer procesoId, @Param("personaId") Integer personaId);

    // @Transactional
    // @Query("SELECT new
    // com.security.dto.TitulacionRevisorNotaLigeroDTO(t.procesoId,
    // t.notaPropuestaProyecto) " +
    // "FROM ProcesoTitulacion t WHERE t.procesoId = :procesoId")
    // TitulacionRevisorNotaLigeroDTO
    // buscarCalificacionPlanTitulacion(@Param("procesoId") Integer procesoId);

}
