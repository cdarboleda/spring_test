package com.security.controller;

import java.util.Comparator;
import java.util.List;

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

import com.security.service.IGestorPasoService;
import com.security.service.IGestorValidacionesService;
import com.security.service.IPasoService;
import com.security.service.dto.PasoDTO;

@RestController
@CrossOrigin
@RequestMapping("/paso")
public class PasoController {

    @Autowired
    private IPasoService pasoService;
    @Autowired
    private IGestorPasoService gestorPasoService;
    @Autowired
    private IGestorValidacionesService gestorValidacionesService;

    /*
     * @GetMapping(path = "/{proceso}", produces = MediaType.APPLICATION_JSON_VALUE)
     * public ResponseEntity<?> crearPasos(@PathVariable String proceso){
     * return new ResponseEntity<>(this.procesoPasoService.crearPasos(proceso),
     * null, HttpStatus.OK);
     * }
     */

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.pasoService.findById(id), null, HttpStatus.OK);
    }

    /*
     * @GetMapping(path = "proceso/{idProceso}", produces =
     * MediaType.APPLICATION_JSON_VALUE)
     * public ResponseEntity<?> buscarPasosDTOPorProcesoId(@PathVariable(name =
     * "idProceso") Integer idProceso) {
     * return new
     * ResponseEntity<>(this.gestorPasoService.findPasosDTOByProcesoId(idProceso),
     * null, HttpStatus.OK);
     * }
     */

    @GetMapping(path = "proceso/{idProceso}/ultimo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarUltimoPasoPorProcesoId(@PathVariable(name = "idProceso") Integer idProceso) {
        List<PasoDTO> pasos = this.gestorPasoService.findPasosDTOByProcesoId(idProceso);

        if (pasos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Obtener el último basado en el ID más alto
        PasoDTO ultimoPaso = pasos.stream()
                .max(Comparator.comparing(PasoDTO::getId))
                .orElse(null);

        return ResponseEntity.ok(ultimoPaso);
    }

    // @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<?> actualizarEstadoPorId(@RequestParam Integer idPaso,
    // @RequestParam String estado){
    // return new ResponseEntity<>(this.pasoService.updateEstado(idPaso, estado),
    // null, HttpStatus.OK);
    // }

    @PutMapping(path = "/{idPaso}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarPasoById(@PathVariable(name = "idPaso") Integer idPaso,
            @RequestBody PasoDTO pasoDTO) {
        return new ResponseEntity<>(this.gestorPasoService.updatePaso(idPaso, pasoDTO), HttpStatus.OK);
    }

    @PutMapping(path = "/{idPaso}/{idResponsable}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarPasoResponsable(@PathVariable(name = "idPaso") Integer idPaso,
            @PathVariable(name = "idResponsable") Integer idResponsable) {
        return new ResponseEntity<>(this.gestorPasoService.updatePasoResponsable(idPaso, idResponsable), HttpStatus.OK);
    }

    @GetMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarTodosEstados() {
        return new ResponseEntity<>(this.pasoService.buscarEstados(), HttpStatus.OK);
    }

    @GetMapping(path = "/proceso/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorIdProceso(@PathVariable Integer id) {
        return new ResponseEntity<>(this.pasoService.findByProcesoId(id), null, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/validar-responsable/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validarUsuarioResponsablePaso(@PathVariable(name = "id") Integer id,
            @PathVariable(name = "idPersona") Integer idPersona) {
        return new ResponseEntity<>(this.gestorValidacionesService.validarAccionPaso(id, idPersona), HttpStatus.OK);
    }

}
