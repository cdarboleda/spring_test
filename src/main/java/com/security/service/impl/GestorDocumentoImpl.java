package com.security.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Documento;
import com.security.exception.CustomException;
import com.security.repo.IDocumentoRepository;
import com.security.service.IGestorDocumento;
import com.security.service.IProcesoService;
import com.security.service.dto.DocumentoDTO;
import com.security.service.dto.DocumentoLigeroDTO;
import com.security.service.dto.utils.ConvertidorDocumento;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorDocumentoImpl implements IGestorDocumento {

    @Autowired
    private IDocumentoRepository documentoRepository;

    @Autowired
    private IProcesoService procesoService;

    @Autowired
    private ConvertidorDocumento convertidorDocumento;

    @Override
    public DocumentoLigeroDTO insert(DocumentoDTO documentoDTO) {
        if (documentoDTO.getProcesoId() == null || documentoDTO == null) {// debe tener un proceso al cual anadirse
            throw new CustomException("Hubo un problema con el Documento, revise su contenido", HttpStatus.BAD_REQUEST);
        }

        Documento documento = new Documento();
        documento.setId(null);// porsiacaso
        documento.setNombre(documentoDTO.getNombre());
        documento.setDescripcion(documentoDTO.getDescripcion());
        documento.setFechaCreacion(LocalDateTime.now());
        documento.setUrl(documentoDTO.getUrl());
        documento.setProceso(this.procesoService.findById(documentoDTO.getProcesoId()));
        Documento documentoActual = this.documentoRepository.save(documento);
        return convertidorDocumento.convertirALigeroDTO(documentoActual);
    }
}
