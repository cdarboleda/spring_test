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

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable(name = "id") Integer id) {
        // return new ResponseEntity<>(this.documentoService.findById(id), null,
        // HttpStatus.OK);
        return new ResponseEntity<>(this.carpetaDocumentoService.findDTOById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/proceso/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarDocumentosPorIdProceso(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.carpetaDocumentoService.findAllByProcesoId(id), null, HttpStatus.OK);
    }

    @GetMapping(path = "/paso/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarDocumentoPorIdPaso(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.carpetaDocumentoService.findByPasoId(id), null, HttpStatus.OK);
    }

    @PutMapping(path = "/paso/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ActualizarDocumentoPorIdPaso(@PathVariable(name = "id") Integer id,
            @RequestBody CarpetaDocumentoDTO carpetaDocumentoDTO) {
        return new ResponseEntity<>(this.gestorCarpetaDocumento.updateUrlByIdPaso(carpetaDocumentoDTO), null,
                HttpStatus.OK);
    }

    // @GetMapping(path="/persona/{id}", produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<?>
    // buscarDocumentosPorIdPersona(@PathVariable(name="id") Integer id){
    // return new
    // ResponseEntity<>(this.carpetaDocumentoService.findAllByPersonaId(id),
    // HttpStatus.OK);
    // }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertar(@RequestBody CarpetaDocumentoDTO documentoDTO) {
        return new ResponseEntity<>(this.gestorCarpetaDocumento.insert(documentoDTO), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@PathVariable(name = "id") Integer id,
            @RequestBody CarpetaDocumentoDTO carpetaDocumentoDTO) {
        carpetaDocumentoDTO.setId(id);
        return new ResponseEntity<>(this.carpetaDocumentoService.updateUrl(carpetaDocumentoDTO), null, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable(name = "id") Integer id) {
        this.carpetaDocumentoService.deleteById(id);
        return new ResponseEntity<>("Carpeta documento con id: " + id + " eliminado", null, HttpStatus.OK);
    }

    @GetMapping(path = "/paso/existe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> existeDocumentoByIdPaso(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.carpetaDocumentoService.existsByIdPaso(id), null, HttpStatus.OK);
    }

}
