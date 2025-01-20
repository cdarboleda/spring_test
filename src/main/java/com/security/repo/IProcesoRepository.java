package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.Proceso;

public interface IProcesoRepository extends JpaRepository<Proceso, Integer>{

    //@Query("SELECT p FROM Proceso p WHERE p.persona.id = :id")
    //List<Proceso> findProcesosWherePersonaIsOwner(@Param("id") Integer id);
    List<Proceso> findByRequirienteId(Integer id);

    //@Query("SELECT pr FROM Proceso pr JOIN pr.personas p WHERE p.id = :id")
    //List<Proceso> findProcesosByPersonaId(@Param("id") Integer id);
    //List<Proceso> findByPersonasId(Integer id);
}
