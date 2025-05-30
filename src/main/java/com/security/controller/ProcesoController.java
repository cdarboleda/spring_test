package com.security.controller;

import java.util.List;

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

import com.security.service.IGestorProcesoService;
import com.security.service.IProcesoService;
import com.security.service.dto.MiProcesoDTO;
import com.security.service.dto.ProcesoDTO;
import com.security.service.dto.ProcesoPagoDocenteResponsablesDTO;

import jakarta.validation.Valid;


@RestController
@CrossOrigin
@RequestMapping("/proceso")
@PreAuthorize("hasAnyRole('administrador', 'usuario')")
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
     public ResponseEntity<?> obtenerProcesoDTOById(@PathVariable(name = "id") Integer id) {
         return new ResponseEntity<>(this.gestorProceso.findByIdCompletoDTO(id), HttpStatus.OK);
     }

    @GetMapping("/mis-procesos")
    public ResponseEntity<?> obtenerMisProcesosPagoDocente() {
        return ResponseEntity.ok(this.gestorProceso.findMisProcesosPagoDocente());
    }

    @GetMapping("/{id}/pasos")
    public ResponseEntity<?> obtenerDetalleProcesoId(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(this.gestorProceso.obtenerDetalleProcesoId(id));
    }

    @PreAuthorize("hasAnyRole('administrador')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> insertarProceso(@Valid @RequestBody ProcesoDTO procesoDTO) {
         return new ResponseEntity<>(this.gestorProceso.insert(procesoDTO), HttpStatus.OK);
     }

    @PreAuthorize("hasAnyRole('administrador')")
    @PostMapping(path="/insertar-pago-docente", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> insertarProcesoPagoDocente(@Valid @RequestBody ProcesoPagoDocenteResponsablesDTO procesoDTO) {
         return new ResponseEntity<>(this.gestorProceso.insertProcesoPagoDocente(procesoDTO), HttpStatus.OK);
     }

     @PreAuthorize("hasAnyRole('administrador')")
     @PutMapping(path="/{id}")
     public ResponseEntity<?> actualizar(@PathVariable(name = "id") Integer id, @RequestBody ProcesoDTO procesoDTO) {
         procesoDTO.setId(id);         
         return new ResponseEntity<>(this.gestorProceso.update(procesoDTO), HttpStatus.OK);
     }

     @PreAuthorize("hasAnyRole('administrador')")
     @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> eliminarProcesoById(@PathVariable(name = "id") Integer id){
        this.gestorProceso.delete(id);
        return new ResponseEntity<>("Proceso con id: "+id+" eliminado", null, HttpStatus.OK);
     }

}
