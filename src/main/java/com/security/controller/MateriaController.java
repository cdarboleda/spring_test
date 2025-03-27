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
import org.springframework.web.bind.annotation.RestController;

import com.security.db.Materia;
import com.security.service.IMateriaService;
import com.security.service.dto.MateriaDTO;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/materia")
@PreAuthorize("hasAnyRole('administrador', 'usuario')")
public class MateriaController {

    @Autowired
    private IMateriaService materiaService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPorId(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(this.materiaService.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('administrador')")
    public ResponseEntity<?> insertar(@Valid @RequestBody MateriaDTO materia) {
        return new ResponseEntity<>(this.materiaService.insert(materia), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('administrador')")
    public ResponseEntity<?> actualizar(@PathVariable(name = "id") Integer id, @RequestBody MateriaDTO materia) {
        materia.setId(id);
        return new ResponseEntity<>(this.materiaService.update(materia), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarTodo() {
        return new ResponseEntity<>(this.materiaService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('administrador')")
    public ResponseEntity<?> eliminar(@PathVariable(name = "id") Integer id) {
        this.materiaService.deleteById(id);
        return new ResponseEntity<>("Eliminado", HttpStatus.OK);
    }

}
