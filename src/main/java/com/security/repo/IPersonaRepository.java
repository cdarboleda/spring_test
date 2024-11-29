package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.db.Persona;
import com.security.db.Rol;

@Repository
public interface IPersonaRepository extends JpaRepository<Persona, Integer>, IPersonaRepositoryCustom{

    @Query("SELECT p.roles FROM Persona p WHERE p.id = :id")
    List<Rol> findRolesByPersonaId(@Param("id") Integer id);
    
}
    
