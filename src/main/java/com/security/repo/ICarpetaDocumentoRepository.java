package com.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.security.db.CarpetaDocumento;

@Repository
public interface ICarpetaDocumentoRepository extends JpaRepository<CarpetaDocumento, Integer> {

    public List<CarpetaDocumento> findByProcesoId(Integer procesoId);

    // public List<CarpetaDocumento> findByPersonaId(Integer personaId);

    public CarpetaDocumento findByPasoId(Integer pasoId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CarpetaDocumento c WHERE c.proceso.id = :procesoId")
    void deleteByProcesoId(@Param("procesoId") Integer procesoId);

    // @Query("SELECT new com.security.service.dto.DocumentoDTO"+
    // "(d.id, d.nombre, d.url, d.descripcion, d.fechaCreacion, p.id, p.nombre) " +
    // "FROM Documento d JOIN d.proceso p WHERE d.id = :id")
    // DocumentoDTO findDTOById(@Param("id") Integer id);

    @Transactional
    @Query("SELECT COUNT(c) > 0 FROM CarpetaDocumento c WHERE c.paso.id = :pasoId")
    public boolean existByIdPaso(Integer pasoId);

}
