package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.db.ProcesoPaso;
import com.security.service.IGestorProcesoPaso;
import com.security.service.IProcesoPasoService;

import jakarta.websocket.server.PathParam;

@RestController
@CrossOrigin
@RequestMapping("/proceso-paso")
public class ProcesoPasoController {

    @Autowired
    private IProcesoPasoService procesoPasoService;

    @Autowired
    private IGestorProcesoPaso gestorProcesoPaso;

    /*@GetMapping(path = "/{proceso}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearPasos(@PathVariable String proceso){
        return new ResponseEntity<>(this.procesoPasoService.crearPasos(proceso), null, HttpStatus.OK);
    }*/

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){
        return new ResponseEntity<>(this.procesoPasoService.findById(id), null, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarPorId(@RequestParam Integer idPaso, @RequestParam Integer idEstado){
        return new ResponseEntity<>(this.gestorProcesoPaso.updateEstado(idPaso, idEstado), null, HttpStatus.OK);
    } 
    
}
