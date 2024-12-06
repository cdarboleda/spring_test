package com.security.service;

import java.util.List;

import com.security.db.Documento;
import com.security.service.dto.DocumentoDTO;
import com.security.service.dto.DocumentoLigeroDTO;

public interface IDocumentoService {
    public Documento findById(Integer id);
    public DocumentoLigeroDTO findDTOById(Integer id);
    public List<DocumentoLigeroDTO> findAllByIdProceso(Integer idProceso);
    public void deleteById(Integer id);
    public DocumentoLigeroDTO update(DocumentoDTO documentoDTO);
}
