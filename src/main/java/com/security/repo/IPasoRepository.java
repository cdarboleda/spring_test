package com.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.security.db.Paso;
import com.security.db.Proceso;
import com.security.service.dto.PasoDTO;

public interface IPasoRepository extends JpaRepository<Paso, Integer> {
    public Optional<Paso> findByOrden(Integer orden);

    List<Paso> findByResponsableId(Integer idResponsable);

    List<Paso> findByProcesoId(Integer idProceso);

    @Transactional
    @Modifying
    @Query("DELETE FROM Paso p WHERE p.proceso.id = :procesoId")
    void deleteByProcesoId(@Param("procesoId") Integer procesoId);

    @Query("SELECT new com.security.service.dto.PasoDTO(" +
            "p.id, p.nombre, p.responsable.id, p.rol.nombre)" +
            "FROM Paso p WHERE p.proceso.id = :procesoId")
    List<PasoDTO> findPasosDTOByProcesoId(@Param("procesoId") Integer procesoId);

}
