package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.IGestorPersonaProceso;
import com.security.service.IProcesoService;

@RestController
@CrossOrigin
@RequestMapping("/proceso")
public class ProcesoController {

     @Autowired
     private IProcesoService procesoService;

     @Autowired
     private IGestorPersonaProceso gestorPersonaProceso;
    
    @GetMapping(path = "/{id}/personas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerProcesosPorPersonaId(@PathVariable Integer id) {
        System.out.println("---------procesos en proceso------------");
        return new ResponseEntity<>(this.gestorPersonaProceso.findProcesosByPersonaId(id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/persona-owner", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerProcesosOwnerDePersona(@PathVariable Integer id) {
        System.out.println("---------persona-owner------------");
        return new ResponseEntity<>(this.gestorPersonaProceso.findProcesosWherePersonaIsOwner(id), HttpStatus.OK);
    }
}
