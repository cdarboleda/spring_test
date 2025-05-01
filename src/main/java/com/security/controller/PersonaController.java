package com.security.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.security.service.IGestorPersonaService;
import com.security.service.IGestorProcesoService;
import com.security.service.IGestorUsurio;
import com.security.service.IPersonaService;
import com.security.service.dto.PersonaDTO;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/persona")
// @PreAuthorize("hasAnyRole('administrador')")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IGestorProcesoService gestorProcesoService;

    @Autowired
    private IGestorPersonaService gestorPersonaService;

    @Autowired
    private IGestorUsurio gestorUsurio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    // CONTROLAR ARREGLAR SOLO MINUSCULAS LAS LETRAS DEL CORREO PARA QUE SEA IGUAL
    // EN
    // KEYCLAOK (4//11/2025)
    public ResponseEntity<?> insertar(@Valid @RequestBody PersonaDTO persona) {
        return new ResponseEntity<>(this.gestorUsurio.createUser(persona), HttpStatus.OK);
    }

    // Buscar necesita la cedula dentro del body
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@PathVariable(name = "id") Integer id, @RequestBody PersonaDTO personaDTO) {
        personaDTO.setId(id);
        // personaDTO.setCedula(cedula);
        return new ResponseEntity<>(this.gestorUsurio.updateUser(personaDTO), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.personaService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/idKeycloak/{idKeycloak}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorIdKeycloak(@PathVariable(name = "idKeycloak") String idKeycloak) {
        Persona persona = personaService.findByIdKeycloak(idKeycloak);
        return persona != null ? ResponseEntity.ok(persona) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    @GetMapping(path = "/cedula/{cedula}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorCedula(@PathVariable(name = "cedula") String cedula) {
        return new ResponseEntity<>(this.personaService.findByCedulaOptional(cedula), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarTodo() {
        List<PersonaDTO> result = this.gestorUsurio.getUsers();
        // System.out.println("result en buscarTodos: " + result);
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }

    // todos, (id, cedula, nombre, apellido)
    @GetMapping(path = "/todos-min", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerTodasLasPersonasMin() {
        return new ResponseEntity<>(this.personaService.findAllPersonaLigeroDTO(), HttpStatus.OK);
    }

    @GetMapping(path = "/todos-min-rol", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerTodasLasPersonasMinRol() {
        return new ResponseEntity<>(this.gestorPersonaService.findAllWithRoles(), HttpStatus.OK);
    }

    @GetMapping("{id}/mis-procesos")
    public ResponseEntity<?> obtenerMisProcesosByResponsableId(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(this.gestorProcesoService.obtenerMisProcesos(id));
    }

    @GetMapping(path = "/{id}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerRolesPorPersonaId(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.gestorPersonaService.findRolesByPersonaId(id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/pasos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPasosPorPersonaId(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.gestorPersonaService.findPasosByPersonaId(id), HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/addPaso", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anadirPaso(@PathVariable(name = "id") Integer id,
            @RequestParam(name = "idPaso") Integer idPaso) {
        this.gestorPersonaService.anadirPaso(id, idPaso);
        return new ResponseEntity<>("Paso " + idPaso + " added", null, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{idKeycloak}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminarPersona(@PathVariable(name = "idKeycloak") String idKeycloak) {
        return new ResponseEntity<>(this.gestorUsurio.deleteUser(idKeycloak), null, HttpStatus.OK);
    }

    @GetMapping(path = "/{cedula}/pasos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPasosPorCedula(@PathVariable String cedula) {

        return new ResponseEntity<>(this.personaService.findByCedula(cedula), HttpStatus.OK);
    }

    @GetMapping(path = "/roles/{rol}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorRol(@PathVariable String rol,
            @RequestParam(required = false) List<Integer> excluirIds) {

        List<Persona> personas = this.gestorPersonaService.findPersonasByRol(rol);
        if (excluirIds != null && !excluirIds.isEmpty()) {
            personas = personas.stream()
                    .filter(p -> !excluirIds.contains(p.getId()))
                    .collect(Collectors.toList());
        }

        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

}
