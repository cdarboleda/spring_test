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

import com.security.service.IGestorProcesoService;
import com.security.service.IProcesoService;
import com.security.service.dto.ProcesoDTO;

import jakarta.validation.Valid;


@RestController
@CrossOrigin
@RequestMapping("/proceso")
public class ProcesoController {

     @Autowired
     private IProcesoService procesoService;

     @Autowired
     private IGestorProcesoService gestorProceso;

    //  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //  public ResponseEntity<?> obtenerProcesoById(@PathVariable Integer id) {
    //      return new ResponseEntity<>(this.procesoService.findById(id), HttpStatus.OK);
    //  }
     @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> obtenerProcesoDTOById(@PathVariable Integer id) {
         return new ResponseEntity<>(this.gestorProceso.findByIdCompletoDTO(id), HttpStatus.OK);
     }

     @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> insertarProceso(@Valid @RequestBody ProcesoDTO procesoDTO) {
         return new ResponseEntity<>(this.gestorProceso.insert(procesoDTO), HttpStatus.OK);
     }
     @PutMapping(path="/{id}")
     public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody ProcesoDTO procesoDTO) {
         procesoDTO.setId(id);         
         return new ResponseEntity<>(this.gestorProceso.update(procesoDTO), HttpStatus.OK);
     }

     @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> eliminarProcesoById(@PathVariable Integer id){
        this.gestorProceso.delete(id);
        return new ResponseEntity<>("Proceso con id: "+id+" eliminado", null, HttpStatus.OK);
     }

}
