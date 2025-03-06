package com.security.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.impl.GestorPersonaServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/admin-titulacion")
public class AdministracionTitulacionController {

    @Autowired
    private GestorPersonaServiceImpl gestorPersonaServiceImpl;

    public void asignarRevisor() {

    }

    public void asignarTutor() {

    }

    public void asignarTribunal() {

    }

    public void asignarLectores() {

    }

    public void asignarFechaDefensa() {

    }

}
