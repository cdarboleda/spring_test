package com.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.db.ProcesoPagoDocente;

import jakarta.transaction.Transactional;

public interface IProcesoPagoDocenteRepository extends JpaRepository<ProcesoPagoDocente, Integer>{
    @Transactional
    @Query("DELETE FROM ProcesoPagoDocente p WHERE p.id = :procesoId")
    @Modifying
    void deleteById(@Param("procesoId") Integer procesoId);

    // @Query("SELECT FROM ProcesoPagoDocente p WHERE p.id = :procesoId")
    // Optional<ProcesoPagoDocente> findById(@Param("procesoId") Integer procesoId);
}
