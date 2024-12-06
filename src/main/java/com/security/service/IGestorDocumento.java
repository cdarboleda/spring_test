package com.security.service;

import com.security.service.dto.DocumentoDTO;
import com.security.service.dto.DocumentoLigeroDTO;

public interface IGestorDocumento {
    DocumentoLigeroDTO insert(DocumentoDTO documentoDTO);
}
