package com.security.service.dto.utils;

import org.springframework.stereotype.Component;

import com.security.db.Documento;
import com.security.service.dto.DocumentoLigeroDTO;

@Component
public class ConvertidorDocumento {

    public DocumentoLigeroDTO convertirALigeroDTO(Documento doc) {
        DocumentoLigeroDTO docDTO = new DocumentoLigeroDTO();
        docDTO.setId(doc.getId());
        docDTO.setNombre(doc.getNombre());
        docDTO.setUrl(doc.getUrl());
        docDTO.setDescripcion(doc.getDescripcion());
        docDTO.setFechaCreacion(doc.getFechaCreacion());
        return docDTO;
    }

    public Documento convertirAEntidad(DocumentoLigeroDTO docDTO) {
        Documento doc = new Documento();
        doc.setId(docDTO.getId());
        doc.setNombre(docDTO.getNombre());
        doc.setUrl(docDTO.getUrl());
        doc.setDescripcion(docDTO.getDescripcion());
        doc.setFechaCreacion(docDTO.getFechaCreacion());
        return doc;
    }

}
