package com.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.db.Persona;

@Repository
public interface IPersonaRepository extends JpaRepository<Persona, Integer> {

    public Optional<Persona> findByCedula(String cedula);

    @Query("SELECT p FROM Persona p WHERE p.correo = :email")
    public Persona findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Persona p JOIN p.roles r WHERE r.id = :rolId")
    public List<Persona> findByRolId(Integer rolId);

    @Query("SELECT p FROM Persona p WHERE p.nombre = :name")
    public List<Persona> findByName(String name);

}
