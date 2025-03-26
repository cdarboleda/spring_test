package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.IMaestriaService;
import com.security.service.dto.MaestriaDTO;

@RestController
@CrossOrigin
@RequestMapping("/maestria")
//@PreAuthorize("hasAnyRole('administrador')")
public class MaestriaContoller {

    @Autowired
    private IMaestriaService maestriaService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertar(@RequestBody MaestriaDTO maestriaDTO){
        return new ResponseEntity<>(this.maestriaService.insert(maestriaDTO), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarTodo() {
        return new ResponseEntity<>(this.maestriaService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@RequestBody MaestriaDTO maestriaDTO){
        return new ResponseEntity<>(this.maestriaService.delete(maestriaDTO), HttpStatus.OK);
    }


    
}
