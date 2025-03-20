package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.impl.EmailPasoRechazado;

@RestController
@RequestMapping("/notificacion")
public class EmailController {

    @Autowired
    private EmailPasoRechazado emailPasoRechazado;

    // @PostMapping(path ="/paso/rechazado", produces = MediaType.APPLICATION_JSON_VALUE)
    // public String notificacionPasoRechazado() {
    //     return this.emailPasoRechazado.send(null, null);
    //     // return this.emailPasoRechazado.send("vavabisga@gmail.com", "Subir documentación del Docente", 45);
    // }

    //esto es del paso Anterior
    //responsableNombre, responsableCorreo, procesoId, tipoProceso, pasoNombre, observaciones
}