package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.db.Proceso;

public interface IProcesoRepository extends JpaRepository<Proceso, Integer>{

    @Query("SELECT p FROM Proceso p WHERE p.persona.id = :id")
    //@Query("SELECT pr FROM Proceso pr JOIN pr.persona o WHERE o.id = :id")
    //@Query("SELECT pr FROM Proceso pr JOIN FETCH pr.persona p WHERE p.id = :id")
    List<Proceso> findProcesosWherePersonaIsOwner(@Param("id") Integer id);

    @Query("SELECT pr FROM Proceso pr JOIN pr.personas p WHERE p.id = :id")
    List<Proceso> findProcesosByPersonaId(@Param("id") Integer id);
    
}
