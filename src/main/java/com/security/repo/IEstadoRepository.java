package com.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.db.Estado;

@Repository
public interface IEstadoRepository extends JpaRepository<Estado, Integer>{

    public Optional<Estado> findByNombre(String nombre);

}