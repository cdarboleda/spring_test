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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.db.Persona;
import com.security.exception.CustomException;
import com.security.service.IGestorPersonaService;
import com.security.service.IGestorProcesoService;
import com.security.service.IPersonaService;
import com.security.service.dto.PersonaDTO;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/persona")
@PreAuthorize("hasAnyRole('admin_client_role', 'secretaria_client_role')")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IGestorProcesoService gestorProcesoService;

    @Autowired
    private IGestorPersonaService gestorPersonaService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertar(@Valid @RequestBody PersonaDTO persona) {
        return new ResponseEntity<>(this.gestorPersonaService.insertar(persona), HttpStatus.OK);
    }

    //Buscar necesita la cedula dentro del body
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody PersonaDTO personaDTO) {
        personaDTO.setId(id);
        //personaDTO.setCedula(cedula);
        return new ResponseEntity<>(this.gestorPersonaService.actualizar(personaDTO), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return new ResponseEntity<>(this.personaService.findById(id), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarTodo() {
        return new ResponseEntity<>(this.personaService.findAll(), null, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/procesos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerProcesosPorPersonaId(@PathVariable Integer id) {
        return new ResponseEntity<>(this.gestorProcesoService.findProcesosByPersonaId(id), HttpStatus.OK);
    }

    @GetMapping(path="/{id}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerRolesPorPersonaId(@PathVariable Integer id) {
        return new ResponseEntity<>(this.gestorPersonaService.findRolesByPersonaId(id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/pasos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPasosPorPersonaId(@PathVariable Integer id){
        return new ResponseEntity<>(this.gestorPersonaService.findPasosByPersonaId(id), HttpStatus.OK);
    }

    @PostMapping(path="/{id}/addPaso", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anadirPaso(@PathVariable Integer id, @RequestParam Integer idPaso) {
        this.gestorPersonaService.anadirPaso(id, idPaso);
        return new ResponseEntity<>("Paso "+idPaso+" added", null, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminarPersona(@PathVariable Integer id){
        this.personaService.deleteById(id);
        return new ResponseEntity<>("Persona eliminada", null, HttpStatus.OK);
    }
    
}
