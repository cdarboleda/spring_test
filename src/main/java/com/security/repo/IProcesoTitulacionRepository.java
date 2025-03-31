package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.db.ProcesoTitulacion;

import jakarta.transaction.Transactional;

public interface IProcesoTitulacionRepository extends JpaRepository<ProcesoTitulacion, Integer> {

    @Transactional
    @Query("UPDATE ProcesoTitulacion pt SET pt.revisorPropuestaProyecto = :nombreRevisor WHERE pt.id = :procesoId")
    @Modifying
    void insertarRevisorProyecto(@Param("procesoId") Integer procesoId, @Param("nombreRevisor") String nombreRevisor);

    @Transactional
    @Query("UPDATE ProcesoTitulacion pt SET pt.tutorPoyecto = :nombreTutor WHERE pt.id = :procesoId")
    @Modifying
    void insertarTutorProyecto(@Param("procesoId") Integer procesoId, @Param("nombreTutor") String nombreTutor);

    @Transactional
    @Query("UPDATE ProcesoTitulacion pt SET pt.tutorPoyecto = :nombreLector WHERE pt.id = :procesoId")
    @Modifying
    void insertarLectorProyecto(@Param("procesoId") Integer procesoId, @Param("nombreLector") String nombreLector);

    @Transactional
    @Query("SELECT COUNT(pt) > 0 FROM ProcesoTitulacion pt JOIN Persona p ON p.id = :personaId WHERE pt.id = :procesoId")
    Boolean existsByIdAndPersonaId(@Param("procesoId") Integer procesoId, @Param("personaId") Integer personaId);

}
