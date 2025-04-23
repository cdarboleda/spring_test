package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.db.ProcesoLog;
import com.security.service.IGestorProcesoLogService;
import com.security.service.IProcesoLogService;

@RestController
@CrossOrigin
@RequestMapping("/proceso-log")
public class ProcesoLogController {

    @Autowired
    private IProcesoLogService procesoLogService;

    @Autowired
    private IGestorProcesoLogService gestorProcesoLogService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertar(@RequestBody ProcesoLog procesoLog) {

        Boolean logInsertado = this.procesoLogService.insert(procesoLog) != null;

        return new ResponseEntity<>(logInsertado, null, HttpStatus.OK);

    }

    @GetMapping(path = "/{id}/proceso")
    public ResponseEntity<?> obtenerLogPorProcesoId(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.procesoLogService.findByProcesoId(id), null, HttpStatus.OK);
    }

    // @GetMapping(path = "/{id}/proceso-paso")
    // public ResponseEntity<?> obtenerLogPorPasoId(@RequestParam Integer idProceso,
    // @RequestParam Integer idPaso) {
    // return new
    // ResponseEntity<>(this.procesoLogService.findByIdProcesoAndIdPaso(idProceso,
    // idPaso),
    // null, HttpStatus.OK);
    // }

    @GetMapping(path = "/{id}/proceso/seguimiento-titulacion")
    public ResponseEntity<?> obtenerLogProcesoFiltradoTitulacion(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.gestorProcesoLogService.buscarLogProcesoFiltradoTitulacion(id), null,
                HttpStatus.OK);
    }

}
