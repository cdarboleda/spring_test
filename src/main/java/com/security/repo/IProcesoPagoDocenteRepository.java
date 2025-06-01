package com.security.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.db.ProcesoPagoDocente;

import jakarta.transaction.Transactional;

public interface IProcesoPagoDocenteRepository extends JpaRepository<ProcesoPagoDocente, Integer> {
    @Transactional
    @Query("DELETE FROM ProcesoPagoDocente p WHERE p.id = :procesoId")
    @Modifying
    void deleteById(@Param("procesoId") Integer procesoId);

    @Query("SELECT COUNT(ppd) > 0 FROM ProcesoPagoDocente ppd " +
            "JOIN ppd.proceso p " +
            "JOIN ppd.materia mat " +
            "JOIN mat.maestria mae " +
            "WHERE mae.id = :maestriaId " +
            "AND mae.cohorte = :cohorte " +
            "AND ppd.fechaInicioClase = :fechaInicioClase " +
            "AND ppd.fechaFinClase = :fechaFinClase " +
            "AND mat.id = :materiaId " +
            "AND p.requiriente.id = :requirienteId")
    boolean existsProcesoPagoDocenteIdentico(
            @Param("requirienteId") Integer requirienteId,
            @Param("maestriaId") Integer maestriaId,
            @Param("cohorte") Integer cohorte,
            @Param("materiaId") Integer materiaId,
            @Param("fechaInicioClase") LocalDate fechaInicioClase,
            @Param("fechaFinClase") LocalDate fechaFinClase);

    // @Query("SELECT FROM ProcesoPagoDocente p WHERE p.id = :procesoId")
    // Optional<ProcesoPagoDocente> findById(@Param("procesoId") Integer procesoId);
}
