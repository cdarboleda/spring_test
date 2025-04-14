package com.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.CarpetaDocumento;
import com.security.exception.CustomException;
import com.security.repo.ICarpetaDocumentoRepository;
import com.security.service.IGestorCarpetaDocumento;
import com.security.service.IPasoService;
import com.security.service.IProcesoService;
import com.security.service.dto.CarpetaDocumentoDTO;
import com.security.service.dto.CarpetaDocumentoLigeroDTO;
import com.security.service.dto.utils.ConvertidorCarpetaDocumento;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GestorCarpetaDocumentoServiceImpl implements IGestorCarpetaDocumento {

    @Autowired
    private ICarpetaDocumentoRepository documentoRepository;

    @Autowired
    private IProcesoService procesoService;

    @Autowired
    private ConvertidorCarpetaDocumento convertidorDocumento;

    @Autowired
    private IPasoService pasoService;

    @Override
    public CarpetaDocumentoLigeroDTO insert(CarpetaDocumentoDTO documentoDTO) {
        if (documentoDTO.getProcesoId() == null || documentoDTO.getPasoId() == null) {// debe tener un proceso/persona
                                                                                      // al cual anadirse
            throw new CustomException("Hubo un problema con el Documento, revise su contenido", HttpStatus.BAD_REQUEST);
        }

        CarpetaDocumento documento = new CarpetaDocumento();
        documento.setId(null);
        documento.setUrl(documentoDTO.getUrl());
        documento.setProceso(this.procesoService.findById(documentoDTO.getProcesoId()));
        documento.setPaso(this.pasoService.findById(documentoDTO.getPasoId()));

        CarpetaDocumento documentoActual = this.documentoRepository.save(documento);

        return convertidorDocumento.convertirALigeroDTO(documentoActual);
    }

}
