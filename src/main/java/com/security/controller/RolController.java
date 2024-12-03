package com.security.controller;

import java.util.List;
import java.util.Optional;

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

import com.security.db.Rol;
import com.security.service.IRolService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private IRolService rolService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rol> insertar(@RequestBody Rol rol){
        return new ResponseEntity<>(this.rolService.insert(rol), null, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rol> buscarPorId(@PathVariable Integer id){

        Rol rol = this.rolService.findById(id).orElseThrow(() -> new EntityNotFoundException("Rol con el id "+ id + " no encontrado."));

        return new ResponseEntity<>(rol , null, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Rol>> buscarRoles(){
        return new ResponseEntity<>(this.rolService.findAll(), null, HttpStatus.OK);
    }
    
}
