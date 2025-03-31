package com.security.service;

import java.util.List;

import com.security.db.CarpetaDocumento;
import com.security.service.dto.CarpetaDocumentoDTO;
import com.security.service.dto.CarpetaDocumentoLigeroDTO;

public interface ICarpetaDocumentoService {
    public CarpetaDocumento findById(Integer id);

    public CarpetaDocumentoLigeroDTO findDTOById(Integer id);

    public List<CarpetaDocumentoLigeroDTO> findAllByProcesoId(Integer idProceso);

    public CarpetaDocumentoLigeroDTO findDTOByPasoId(Integer idPaso);

    public CarpetaDocumento findByPasoId(Integer idPaso);

    // public List<CarpetaDocumentoLigeroDTO> findAllByPersonaId(Integer idPersona);
    public void deleteById(Integer id);

    public CarpetaDocumentoLigeroDTO updateUrl(CarpetaDocumentoDTO documentoDTO);

    public Boolean existsByIdPaso(Integer pasoId);

}
