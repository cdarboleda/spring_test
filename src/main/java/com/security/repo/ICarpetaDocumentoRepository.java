package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.db.CarpetaDocumento;

@Repository
public interface ICarpetaDocumentoRepository extends JpaRepository<CarpetaDocumento, Integer>{
    
    public List<CarpetaDocumento> findByProcesoId(Integer procesoId);
    public List<CarpetaDocumento> findByPersonaId(Integer personaId);

    // @Query("SELECT new com.security.service.dto.DocumentoDTO"+
    // "(d.id, d.nombre, d.url, d.descripcion, d.fechaCreacion, p.id, p.nombre) " +
    // "FROM Documento d JOIN d.proceso p WHERE d.id = :id")
    // DocumentoDTO findDTOById(@Param("id") Integer id);

}
