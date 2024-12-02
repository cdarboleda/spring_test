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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.db.Persona;
import com.security.service.IGestorPersonaRol;
import com.security.service.IPersonaService;
import com.security.service.dto.PersonaDTO;



@RestController
@CrossOrigin
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IGestorPersonaRol personaRol;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> insertar(@RequestBody PersonaDTO persona){
        Persona personaTmp = this.personaRol.insertar(persona);
        return new ResponseEntity<>(personaTmp, null, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> actualizar(@PathVariable Integer id, @RequestBody PersonaDTO personaDTO){
        personaDTO.setId(id);
        Persona personaTmp = this.personaRol.actualizar(personaDTO);
        return new ResponseEntity<>(personaTmp, null, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> buscarPorId(@PathVariable Integer id) {
        Persona persona = this.personaService.findByIdPerson(id)
                .orElseThrow(() -> new RuntimeException("Persona con id: " + id + " no encontrada"));

        return new ResponseEntity<>(persona, null, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Persona>> buscarTodo(){
        return new ResponseEntity<>(this.personaService.findAll(), null, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerRolesPorPersonaId(@PathVariable Integer id) {
        return new ResponseEntity<>(this.personaService.findRolesByPersonId(id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/procesos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerProcesosPorPersonaId(@PathVariable Integer id) {
        return new ResponseEntity<>(this.personaService.findProcesosByPersonaId(id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/procesos-owner", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerProcesosOwnerDePersona(@PathVariable Integer id) {
        return new ResponseEntity<>(this.personaService.findProcesosWherePersonaIsOwner(id), HttpStatus.OK);
    }
}
