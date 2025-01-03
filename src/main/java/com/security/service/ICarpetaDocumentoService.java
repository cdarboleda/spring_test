package com.security.service;

import java.util.List;

import com.security.db.CarpetaDocumento;
import com.security.service.dto.CarpetaDocumentoDTO;
import com.security.service.dto.CarpetaDocumentoLigeroDTO;

public interface ICarpetaDocumentoService {
    public CarpetaDocumento findById(Integer id);
    public CarpetaDocumentoLigeroDTO findDTOById(Integer id);
    public List<CarpetaDocumentoLigeroDTO> findAllByIdProceso(Integer idProceso);
    public void deleteById(Integer id);
    public CarpetaDocumentoLigeroDTO update(CarpetaDocumentoDTO documentoDTO);
}
