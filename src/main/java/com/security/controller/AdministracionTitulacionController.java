package com.security.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.PersonaTitulacionLigeroDTO;
import com.security.service.impl.GestorPersonaServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/admin-titulacion")
public class AdministracionTitulacionController {

    @Autowired
    private IProcesoTitulacionService procesoTitulacionService;


    // recibe el id del proceso y la persona a agregar a titulacion (se verifica que
    // existan) (sea tutor,
    // revisor o lector Depende el rol de la persona)
    @PostMapping("/agregar-persona")
    public ResponseEntity<String> añadirPersonaTitulacion(
            @RequestBody PersonaTitulacionLigeroDTO personaTitulacionDTO) {
        try {
            // Reutiliza la lógica del servicio para insertar un proceso de titulación
            this.procesoTitulacionService.AgregarTutorRevisor(
                    personaTitulacionDTO);
            return ResponseEntity.ok("Persona agregada con éxito a TITULACION.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la persona en Titulacion " + e.getMessage());
        }
    }

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
