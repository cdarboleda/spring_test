package com.security.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.CarpetaDocumento;
import com.security.exception.CustomException;
import com.security.repo.ICarpetaDocumentoRepository;
import com.security.service.IGestorCarpetaDocumento;
import com.security.service.IProcesoService;
import com.security.service.dto.CarpetaDocumentoDTO;
import com.security.service.dto.CarpetaDocumentoLigeroDTO;
import com.security.service.dto.utils.ConvertidorCarpetaDocumento;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorDocumentoImpl implements IGestorCarpetaDocumento {

    @Autowired
    private ICarpetaDocumentoRepository documentoRepository;

    @Autowired
    private IProcesoService procesoService;

    @Autowired
    private ConvertidorCarpetaDocumento convertidorDocumento;

    @Override
    public CarpetaDocumentoLigeroDTO insert(CarpetaDocumentoDTO documentoDTO) {
        if (documentoDTO.getProcesoId() == null || documentoDTO == null) {// debe tener un proceso al cual anadirse
            throw new CustomException("Hubo un problema con el Documento, revise su contenido", HttpStatus.BAD_REQUEST);
        }

        CarpetaDocumento documento = new CarpetaDocumento();
        documento.setId(null);// porsiacaso
        documento.setTipo(documentoDTO.getTipo());
        documento.setUrl(documentoDTO.getUrl());
        documento.setProceso(this.procesoService.findById(documentoDTO.getProcesoId()));
        CarpetaDocumento documentoActual = this.documentoRepository.save(documento);
        return convertidorDocumento.convertirALigeroDTO(documentoActual);
    }
}
