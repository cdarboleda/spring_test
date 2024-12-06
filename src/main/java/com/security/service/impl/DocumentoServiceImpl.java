package com.security.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Documento;
import com.security.repo.IDocumentoRepository;
import com.security.service.IDocumentoService;
import com.security.service.dto.DocumentoDTO;
import com.security.service.dto.DocumentoLigeroDTO;
import com.security.service.dto.utils.ConvertidorDocumento;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DocumentoServiceImpl implements IDocumentoService {

    @Autowired
    private IDocumentoRepository documentoRepository;
    @Autowired
    private ConvertidorDocumento convertidorDocumento;

    //Este devuelve el documento sin nada de info de su proceso, por el json ignore
    @Override
    public Documento findById(Integer id) {
        Documento documento = this.documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay documento con id: " + id));
        return documento;

    }

    @Override
    public DocumentoLigeroDTO findDTOById(Integer id) {
        //DocumentoLigeroDTO documento = this.documentoRepository.findDTOById(id);
        Documento documento = this.findById(id);
        return convertidorDocumento.convertirALigeroDTO(documento);
    }

    @Override
    public List<DocumentoLigeroDTO> findAllByIdProceso(Integer idProceso) {
        List<Documento> documentos = this.documentoRepository.findByProcesoId(idProceso);
        if (documentos.isEmpty()) {
            throw new EntityNotFoundException("No hay documentos para el proceso con id: " + idProceso);
        }
        List<DocumentoLigeroDTO> documentosLigeros = documentos.stream().map(convertidorDocumento::convertirALigeroDTO).collect(Collectors.toList());
        return documentosLigeros;
    }

    @Override
    public void deleteById(Integer id) {
        if (!this.documentoRepository.existsById(id)) {
            throw new EntityNotFoundException("No hay documento con id: " + id);
        }
        this.documentoRepository.deleteById(id);
    }

    //La logica de negocio no me deberia dejar cambiar de idProceso
    @Override
    public DocumentoLigeroDTO update(DocumentoDTO documentoDTO) {
        if (documentoDTO == null) {
            throw new IllegalArgumentException("Hubo un problema con el Documento, revise su contenido");
        }
        Documento documentoActual = this.findById(documentoDTO.getId()); 
        documentoActual.setNombre(documentoDTO.getNombre());
        documentoActual.setDescripcion(documentoDTO.getDescripcion());
        documentoActual.setUrl(documentoDTO.getUrl());
        return convertidorDocumento.convertirALigeroDTO(documentoActual);
    }

}
