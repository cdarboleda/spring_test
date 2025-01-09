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
import com.security.service.IGestorPersonaRol;
import com.security.service.IGestorProceso;
import com.security.service.IPersonaService;
import com.security.service.dto.PersonaDTO;

@RestController
@CrossOrigin
@RequestMapping("/persona")
@PreAuthorize("hasAnyRole('admin_client_role', 'secretaria_client_role')")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IGestorPersonaRol personaRol;

    @Autowired
    private IGestorProceso gestorProceso;

    @Autowired
    private IGestorPersonaRol gestorPersonaRol;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertar(@RequestBody PersonaDTO persona) {
        Persona personaTmp = this.personaRol.insertar(persona);
        return new ResponseEntity<>(personaTmp, null, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody PersonaDTO personaDTO) {
        personaDTO.setId(id);
        Persona personaTmp = this.personaRol.actualizar(personaDTO);
        return new ResponseEntity<>(personaTmp, null, HttpStatus.OK);
    }

    @PutMapping(path = "/add-roles",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarRoles(@RequestBody PersonaDTO dto){
        return new ResponseEntity<>(this.gestorPersonaRol.addRolToPersona(dto), null, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Persona persona = this.personaService.findByIdPerson(id)
                .orElseThrow(() -> new RuntimeException("Persona con id: " + id + " no encontrada"));

        return new ResponseEntity<>(persona, null, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarTodo() {
        return new ResponseEntity<>(this.personaService.findAll(), null, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/procesos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerProcesosPorPersonaId(@PathVariable Integer id) {
        return new ResponseEntity<>(this.gestorProceso.findProcesosByPersonaId(id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/procesos-requiriente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerProcesosDePersonaRequiriente(@PathVariable Integer id) {
        return new ResponseEntity<>(this.gestorProceso.findProcesosWherePersonaIsOwner(id), HttpStatus.OK);
    }

    @GetMapping(path="/{id}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerRolesPorPersonaId(@PathVariable Integer id) {
        return new ResponseEntity<>(this.gestorPersonaRol.findRolesByPersonaId(id), null, HttpStatus.OK);
    }


    @PostMapping(path="/{id}/addPaso", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anadirPaso(@PathVariable Integer id, @RequestParam Integer idPaso) {
        this.gestorPersonaRol.anadirPaso(id, idPaso);
        return new ResponseEntity<>("Paso "+idPaso+" added", null, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminarPersona(@PathVariable Integer id){
        this.personaService.deleteById(id);
        return new ResponseEntity<>("Persona eliminada", null, HttpStatus.OK);
    }
    
}
