package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.db.Persona;
import com.security.service.IPersonaService;

@RestController
@CrossOrigin
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    public ResponseEntity<Persona> buscarPorId(@PathVariable Integer id) {
        Persona persona = this.personaService.findByIdPerson(id)
                .orElseThrow(() -> new RuntimeException("Persona con id: " + id + " no encontrada"));

        Persona persona1 = this.personaService.findByIdPerson(id).get();

        return new ResponseEntity<>(persona, null, HttpStatus.OK);
    }

}
