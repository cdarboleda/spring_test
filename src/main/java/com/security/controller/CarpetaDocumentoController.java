package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.ICarpetaDocumentoService;
import com.security.service.IGestorCarpetaDocumento;
import com.security.service.dto.CarpetaDocumentoDTO;

@RestController
@CrossOrigin
@RequestMapping("/carpeta-documento")
public class CarpetaDocumentoController {

    @Autowired
    private ICarpetaDocumentoService carpetaDocumentoService;

    @Autowired
    private IGestorCarpetaDocumento gestorCarpetaDocumento;

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){
        //return new ResponseEntity<>(this.documentoService.findById(id), null, HttpStatus.OK);
        return new ResponseEntity<>(this.carpetaDocumentoService.findDTOById(id), null, HttpStatus.OK);
    }
    @GetMapping(path="/proceso/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarDocumentoPorIdProceso(@PathVariable Integer id){
        return new ResponseEntity<>(this.carpetaDocumentoService.findAllByIdProceso(id), null, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertar(@RequestBody CarpetaDocumentoDTO documentoDTO){
        return new ResponseEntity<>(this.gestorCarpetaDocumento.insert(documentoDTO), null, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody CarpetaDocumentoDTO carpetaDocumentoDTO){
        carpetaDocumentoDTO.setId(id);
        return new ResponseEntity<>(this.carpetaDocumentoService.update(carpetaDocumentoDTO), null, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        this.carpetaDocumentoService.deleteById(id);
        return new ResponseEntity<>("Carpeta documento con id: "+id+" eliminado", null, HttpStatus.OK);
    }
    
}
