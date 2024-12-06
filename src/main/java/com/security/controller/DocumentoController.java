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

import com.security.service.IDocumentoService;
import com.security.service.IGestorDocumento;
import com.security.service.dto.DocumentoDTO;

@RestController
@CrossOrigin
@RequestMapping("/documento")
public class DocumentoController {

    @Autowired
    private IDocumentoService documentoService;

    @Autowired
    private IGestorDocumento gestorDocumento;

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){
        //return new ResponseEntity<>(this.documentoService.findById(id), null, HttpStatus.OK);
        return new ResponseEntity<>(this.documentoService.findDTOById(id), null, HttpStatus.OK);
    }
    @GetMapping(path="/proceso/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarDocumentoPorIdProceso(@PathVariable Integer id){
        return new ResponseEntity<>(this.documentoService.findAllByIdProceso(id), null, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertar(@RequestBody DocumentoDTO documentoDTO){
        return new ResponseEntity<>(this.gestorDocumento.insert(documentoDTO), null, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody DocumentoDTO documentoDTO){
        documentoDTO.setId(id);
        return new ResponseEntity<>(this.documentoService.update(documentoDTO), null, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        this.documentoService.deleteById(id);
        return new ResponseEntity<>("Documento con id: "+id+" eliminado", null, HttpStatus.OK);
    }
    
}
