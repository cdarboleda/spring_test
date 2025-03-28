package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.IMaestriaService;
import com.security.service.dto.MaestriaDTO;

@RestController
@CrossOrigin
@RequestMapping("/maestria")
// @PreAuthorize("hasAnyRole('administrador')")
public class MaestriaContoller {

    @Autowired
    private IMaestriaService maestriaService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertar(@RequestBody MaestriaDTO maestriaDTO) {
        return new ResponseEntity<>(this.maestriaService.insert(maestriaDTO), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarTodo() {
        return new ResponseEntity<>(this.maestriaService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{maestriaId}/{maestriaDetalleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable(name="maestriaId") Integer maestriaId, @PathVariable(name="maestriaDetalleId") Integer maestriaDetalleId) {
        MaestriaDTO maestriaDTO = new MaestriaDTO();
        maestriaDTO.setMaestriaId(maestriaId);
        maestriaDTO.setMaestriaDetalleId(maestriaDetalleId);
        return new ResponseEntity<>(this.maestriaService.delete(maestriaDTO), HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody MaestriaDTO maestriaDTO){
        return new ResponseEntity<>(this.maestriaService.update(maestriaDTO), HttpStatus.OK);
    }

}
