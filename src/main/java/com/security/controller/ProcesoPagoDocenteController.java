package com.security.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.IGestorProcesoService;

@RestController
@CrossOrigin
@RequestMapping("/proceso-pago-docente")
@PreAuthorize("hasAnyRole('administrador', 'usuario')")
public class ProcesoPagoDocenteController {
    @Autowired
    private IGestorProcesoService gestorProceso;

    @GetMapping(path = "/existe-identico")
    @PreAuthorize("hasAnyRole('administrador')")
    public ResponseEntity<?> verificarProcesoUnico(
            @RequestParam("requirienteId") Integer requirienteId,
            @RequestParam("maestriaId") Integer maestriaId,
            @RequestParam("cohorte") Integer cohorte,
            @RequestParam("materiaId") Integer materiaId,
            @RequestParam("fechaInicioClase") LocalDate fechaInicioClase,
            @RequestParam("fechaFinClase") LocalDate fechaFinClase) {

        return new ResponseEntity<>(

                this.gestorProceso.existsProcesoPagoDocenteIdentico(requirienteId, maestriaId, cohorte, materiaId,
                        fechaInicioClase, fechaFinClase),
                HttpStatus.OK);
    }
}