package com.security.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.IGestorPasoService;
import com.security.service.IPasoService;
import com.security.service.dto.PasoDTO;
import com.security.service.impl.EmailPasoRechazado;

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
    @PreAuthorize("hasAnyRole('administrador', 'usuario')")
    public ResponseEntity<?> buscarPorId(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.pasoService.findById(id), null, HttpStatus.OK);
    }

    @GetMapping(path = "proceso/{idProceso}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('administrador', 'usuario')")
    public ResponseEntity<?> buscarPasosDTOPorProcesoId(@PathVariable(name = "idProceso") Integer idProceso) {
        return new ResponseEntity<>(this.gestorPasoService.findPasosDTOByProcesoId(idProceso), null, HttpStatus.OK);
    }

    @GetMapping(path = "proceso/{idProceso}/ultimo", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('administrador', 'usuario')")
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
    @PreAuthorize("hasAnyRole('administrador', 'usuario')")
    public ResponseEntity<?> actualizarPasoById(@PathVariable(name = "idPaso") Integer idPaso,
            @RequestBody PasoDTO pasoDTO) {
        return new ResponseEntity<>(this.gestorPasoService.updatePaso(idPaso, pasoDTO), HttpStatus.OK);
    }

    @PutMapping(path = "/{idPaso}/{idResponsable}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('administrador')")
    public ResponseEntity<?> actualizarPasoResponsable(@PathVariable(name = "idPaso") Integer idPaso,
            @PathVariable(name = "idResponsable") Integer idResponsable) {
        return new ResponseEntity<>(this.gestorPasoService.updatePasoResponsable(idPaso, idResponsable), HttpStatus.OK);
    }

    @PutMapping(path = "actualizar-multiple-responsable/{idResponsable}/{rol}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('administrador', 'usuario')")
    public ResponseEntity<?> actualizarPasoResponsable(@PathVariable(name = "idResponsable") Integer idResponsable,
            @PathVariable(name = "rol") String rol,
            @RequestBody List<Integer> pasosIds) {
        return new ResponseEntity<>(this.gestorPasoService.updatePasosMismoResponsable(pasosIds, idResponsable, rol),
                HttpStatus.OK);
    }

    @GetMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('administrador', 'usuario')")
    public ResponseEntity<?> buscarTodosEstados() {
        return new ResponseEntity<>(this.pasoService.buscarEstados(), HttpStatus.OK);
    }

    @PutMapping(path = "/rechazar/{idPasoActual}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('administrador', 'usuario')")
    public ResponseEntity<?> rechazarPaso(@PathVariable(name = "idPasoActual") Integer idPasoActual,
            @RequestBody(required = false) PasoDTO pasoAnteriorDTO,
            @RequestParam(name = "observacionesString") String observacionesString,
            @RequestParam(name = "maestria") String maestria, @RequestParam(name = "materia") String materia) {
        return new ResponseEntity<>(
                this.gestorPasoService.rechazarPaso(idPasoActual, pasoAnteriorDTO, observacionesString, maestria, materia),
                HttpStatus.OK);
    }

    // esto es del paso Anterior
    // responsableNombre, responsableCorreo, procesoId, tipoProceso, pasoNombre,
    // observaciones
    @Autowired
    private EmailPasoRechazado emailPasoRechazado;

    // @PostMapping("/send")
    // public String sendEmail() {
    // return this.emailPasoRechazado.send("vavabisga@gmail.com", "Subir
    // documentación del Docente", 45);
    // }

}
