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
import org.springframework.web.bind.annotation.RestController;

import com.security.db.Persona;
import com.security.service.IPersonaService;

@RestController
@CrossOrigin
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> insertar(@RequestBody Persona persona){

        Persona personaTmp = this.personaService.insert(persona);

        return new ResponseEntity<>(personaTmp, null, HttpStatus.OK);

    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> buscarPorId(@PathVariable Integer id) {
        Persona persona = this.personaService.findByIdPerson(id)
                .orElseThrow(() -> new RuntimeException("Persona con id: " + id + " no encontrada"));

        //Persona persona1 = this.personaService.findByIdPerson(id).get();
        //System.out.println("amogu------------------------------");

        return new ResponseEntity<>(persona, null, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Persona>> buscarTodo(){
        return new ResponseEntity<>(this.personaService.findAll(), null, HttpStatus.OK);
    }

}
