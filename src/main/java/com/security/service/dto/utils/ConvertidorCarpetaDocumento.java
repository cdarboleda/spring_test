package com.security.service.dto.utils;

import org.springframework.stereotype.Component;

import com.security.db.CarpetaDocumento;
import com.security.service.dto.CarpetaDocumentoLigeroDTO;

@Component
public class ConvertidorCarpetaDocumento {

    public CarpetaDocumentoLigeroDTO convertirALigeroDTO(CarpetaDocumento doc) {
        CarpetaDocumentoLigeroDTO docDTO = new CarpetaDocumentoLigeroDTO();
        docDTO.setId(doc.getId());
        docDTO.setUrl(doc.getUrl());
        return docDTO;
    }

    public CarpetaDocumento convertirAEntidad(CarpetaDocumentoLigeroDTO docDTO) {
        CarpetaDocumento doc = new CarpetaDocumento();
        doc.setId(docDTO.getId());
        doc.setUrl(docDTO.getUrl());
        return doc;
    }

}
