package com.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.db.Rol;
@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer>{

    public Optional<Rol> findByNombre(String nombre);

    //Este hace lo mismo que el de abajo
    public List<Rol> findByPersonasId(Integer id);

    public List<Rol> findByNombreIn(List<String> names);

    // @Query("SELECT r FROM Rol r JOIN r.personas p WHERE p.id = :id")
    // List<Rol> findRolesByPersonaId(@Param("id") Integer id);
    
}
