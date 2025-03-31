package com.security.service;

import com.security.service.dto.CarpetaDocumentoDTO;
import com.security.service.dto.CarpetaDocumentoLigeroDTO;

public interface IGestorCarpetaDocumento {
    CarpetaDocumentoLigeroDTO insert(CarpetaDocumentoDTO documentoDTO);

    CarpetaDocumentoLigeroDTO updateUrlByIdPaso(CarpetaDocumentoDTO documentoDTO);
}
