package com.security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.CarpetaDocumento;
import com.security.db.Paso;
import com.security.db.enums.Estado;
import com.security.exception.CustomException;
import com.security.repo.ICarpetaDocumentoRepository;
import com.security.service.ICarpetaDocumentoService;
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
    private ICarpetaDocumentoService documentoService;
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
        // documento.setPersona(this.personaService.findById(documentoDTO.getPersonaId()));
        documento.setPaso(this.pasoService.findById(documentoDTO.getPasoId()));

        CarpetaDocumento documentoActual = this.documentoRepository.save(documento);

        // this.pasoService.updateEstado(documentoDTO.getPasoId(),
        // documentoDTO.getEstado());

        return convertidorDocumento.convertirALigeroDTO(documentoActual);
    }

    @Override
    public CarpetaDocumentoLigeroDTO updateUrlByIdPaso(CarpetaDocumentoDTO documentoDTO) {
        // TODO Auto-generated method stub
        /*
         * Optional<Paso> paso =
         * this.pasoService.findByIdOptional(documentoDTO.getPasoId());
         * 
         * if (paso.get().getDescripcionEstado().equals(Estado.FINALIZADO.
         * getDescripcionPorIndice(0)) // si esta "Completado" no debe poder actualizar
         * || paso.get().getDescripcionEstado().equals(Estado.EN_CURSO.
         * getDescripcionPorIndice(0))) { // si esta "En curso" no debe poder actualizar
         * throw new
         * CustomException("El paso no puede actualizarse revise el estado y la descripcion"
         * ,
         * HttpStatus.BAD_REQUEST);
         * }
         */
        CarpetaDocumento documento = this.documentoService.findByPasoId(documentoDTO.getPasoId());
        documento.setUrl(documentoDTO.getUrl());
        CarpetaDocumento documentoActual = this.documentoRepository.save(documento);
        return convertidorDocumento.convertirALigeroDTO(documentoActual);
    }

}
