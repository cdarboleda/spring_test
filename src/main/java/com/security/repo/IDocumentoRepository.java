package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.db.Documento;
import com.security.service.dto.DocumentoDTO;

@Repository
public interface IDocumentoRepository extends JpaRepository<Documento, Integer>{
    
    List<Documento> findByProcesoId(Integer procesoId);

    // @Query("SELECT new com.security.service.dto.DocumentoDTO"+
    // "(d.id, d.nombre, d.url, d.descripcion, d.fechaCreacion, p.id, p.nombre) " +
    // "FROM Documento d JOIN d.proceso p WHERE d.id = :id")
    // DocumentoDTO findDTOById(@Param("id") Integer id);

}
