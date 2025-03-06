package com.security.controller;

import java.util.List;

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
import com.security.service.dto.MiProcesoDTO;
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
     public ResponseEntity<?> obtenerProcesoDTOById(@PathVariable(name = "id") Integer id) {
         return new ResponseEntity<>(this.gestorProceso.findByIdCompletoDTO(id), HttpStatus.OK);
     }

    @GetMapping("/mis-procesos")
    public ResponseEntity<?> obtenerMisProcesos() {
        return ResponseEntity.ok(this.gestorProceso.findMisProcesos());
    }


    // @GetMapping("/mis-procesos/{id}")
    // public ResponseEntity<?> obtenerMisProcesosByResponsableId(@PathVariable
    // Integer id) {
    // return
    // ResponseEntity.ok(this.gestorProceso.findMisProcesosByResponsableId(id));
    // }

    @GetMapping("/mis-procesos/{id}")
    public ResponseEntity<?> obtenerMisProcesosByResponsableId(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(this.gestorProceso.obtenerMisProcesos(id));
    }

    @GetMapping("/{id}/pasos")
    public ResponseEntity<?> obtenerDetalleProcesoId(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(this.gestorProceso.obtenerDetalleProcesoId(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> insertarProceso(@Valid @RequestBody ProcesoDTO procesoDTO) {
        //     this.gestorProceso.insert(procesoDTO);
        //  return new ResponseEntity<>(null, HttpStatus.OK);
         return new ResponseEntity<>(this.gestorProceso.insert(procesoDTO), HttpStatus.OK);
     }
     @PutMapping(path="/{id}")
     public ResponseEntity<?> actualizar(@PathVariable(name = "id") Integer id, @RequestBody ProcesoDTO procesoDTO) {
         procesoDTO.setId(id);         
         return new ResponseEntity<>(this.gestorProceso.update(procesoDTO), HttpStatus.OK);
     }

     @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> eliminarProcesoById(@PathVariable(name = "id") Integer id){
        this.gestorProceso.delete(id);
        return new ResponseEntity<>("Proceso con id: " + id + " eliminado", null, HttpStatus.OK);
    }

}
