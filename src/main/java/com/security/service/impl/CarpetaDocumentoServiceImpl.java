package com.security.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.CarpetaDocumento;
import com.security.repo.ICarpetaDocumentoRepository;
import com.security.service.ICarpetaDocumentoService;
import com.security.service.dto.CarpetaDocumentoDTO;
import com.security.service.dto.CarpetaDocumentoLigeroDTO;
import com.security.service.dto.utils.ConvertidorCarpetaDocumento;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarpetaDocumentoServiceImpl implements ICarpetaDocumentoService {

    @Autowired
    private ICarpetaDocumentoRepository carpetaDocumentoRepository;
    @Autowired
    private ConvertidorCarpetaDocumento convertidorCarpetaDocumento;


    //Este devuelve el documento sin nada de info de su proceso, por el json ignore
    @Override
    public CarpetaDocumento findById(Integer id) {
        CarpetaDocumento documento = this.carpetaDocumentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay documento con id: " + id));
        return documento;

    }

    @Override
    public CarpetaDocumentoLigeroDTO findDTOById(Integer id) {
        //DocumentoLigeroDTO documento = this.documentoRepository.findDTOById(id);
        CarpetaDocumento documento = this.findById(id);
        return convertidorCarpetaDocumento.convertirALigeroDTO(documento);
    }

    @Override
    public List<CarpetaDocumentoLigeroDTO> findAllByProcesoId(Integer idProceso) {
        List<CarpetaDocumento> documentos = this.carpetaDocumentoRepository.findByProcesoId(idProceso);
        if (documentos.isEmpty()) {
            throw new EntityNotFoundException("No hay carpeta documentos para el proceso con id: " + idProceso);
        }
        List<CarpetaDocumentoLigeroDTO> documentosLigeros = documentos.stream().map(convertidorCarpetaDocumento::convertirALigeroDTO).collect(Collectors.toList());
        return documentosLigeros;
    }

    // @Override
    // public List<CarpetaDocumentoLigeroDTO> findAllByPersonaId(Integer idPersona) {
    //     List<CarpetaDocumento> documentos = this.carpetaDocumentoRepository.findByPersonaId(idPersona);
    //     if (documentos.isEmpty()) {
    //         throw new EntityNotFoundException("No hay carpeta documentos para la persona con id: " + idPersona);
    //     }
    //     List<CarpetaDocumentoLigeroDTO> documentosLigeros = documentos.stream().map(convertidorCarpetaDocumento::convertirALigeroDTO).collect(Collectors.toList());
    //     return documentosLigeros;
    // }

    @Override
    public void deleteById(Integer id) {
        if (!this.carpetaDocumentoRepository.existsById(id)) {
            throw new EntityNotFoundException("No hay carpeta documento con id: " + id);
        }
        this.carpetaDocumentoRepository.deleteById(id);
    }

    //La logica de negocio no me deberia dejar cambiar de idProceso
    @Override
    public CarpetaDocumentoLigeroDTO updateUrl(CarpetaDocumentoDTO documentoDTO) {
        if (documentoDTO == null) {
            throw new IllegalArgumentException("Hubo un problema con la carpeta documento, revise su contenido");
        }
        CarpetaDocumento documentoActual = this.findById(documentoDTO.getId()); 
        documentoActual.setUrl(documentoDTO.getUrl());
        return convertidorCarpetaDocumento.convertirALigeroDTO(documentoActual);
    }

}
