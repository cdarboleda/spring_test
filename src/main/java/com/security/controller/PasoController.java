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

import com.security.db.Paso;
import com.security.service.IGestorPasoService;
import com.security.service.IPasoService;

import jakarta.websocket.server.PathParam;

@RestController
@CrossOrigin
@RequestMapping("/paso")
public class PasoController {

    @Autowired
    private IPasoService pasoService;
    @Autowired
    private IGestorPasoService gestorPasoService;

    /*
     * @GetMapping(path = "/{proceso}", produces = MediaType.APPLICATION_JSON_VALUE)
     * public ResponseEntity<?> crearPasos(@PathVariable String proceso){
     * return new ResponseEntity<>(this.procesoPasoService.crearPasos(proceso),
     * null, HttpStatus.OK);
     * }
     */

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return new ResponseEntity<>(this.pasoService.findById(id), null, HttpStatus.OK);
    }

    // @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<?> actualizarEstadoPorId(@RequestParam Integer idPaso,
    // @RequestParam String estado){
    // return new ResponseEntity<>(this.pasoService.updateEstado(idPaso, estado),
    // null, HttpStatus.OK);
    // }

    @PutMapping(path = "/{idPaso}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarEstadoPorId(@PathVariable Integer idPaso, @PathVariable String estado) {
        return new ResponseEntity<>(this.pasoService.updateEstado(idPaso, estado), null, HttpStatus.OK);
    }

    @GetMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarTodosEstados() {
        return new ResponseEntity<>(this.pasoService.buscarEstados(), HttpStatus.OK);
    }

    @GetMapping(path = "/proceso/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorIdProceso(@PathVariable Integer id) {
        return new ResponseEntity<>(this.pasoService.findByProcesoId(id), null, HttpStatus.OK);
    }

}
