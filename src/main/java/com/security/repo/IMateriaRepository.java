package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.db.Materia;
import com.security.service.dto.MateriaTablaDTO;

public interface IMateriaRepository extends JpaRepository<Materia, Integer> {
            @Query("SELECT new com.security.service.dto.MateriaTablaDTO( " +
                        "mat.id, mat.codigo, mat.nombre, mat.periodo, "+
                        "mae.id, mae.maestria.nombre, mae.maestria.codigo, " +
                        "mae.cohorte, mae.fechaInicio, mae.fechaFin) " +
                        "FROM Materia mat " +
                        "LEFT JOIN mat.maestriaDetalle mae ")
        List<MateriaTablaDTO> findMateriasTabla();
}
