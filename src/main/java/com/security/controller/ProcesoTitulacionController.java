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
import com.security.service.IGestorProcesoService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoService;
import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.PersonaLigeroDTO;
import com.security.service.dto.ProcesoTitulacionDTO;
import com.security.service.dto.PersonaTitulacionLigeroDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/titulacion")
public class ProcesoTitulacionController {

    private final PersonaController personaController;

    @Autowired
    private GestorPersonaServiceImpl gestorPersonaServiceImpl;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IProcesoService procesoService;

    @Autowired
    private IGestorProcesoService gestorProcesoService;

    @Autowired
    private IProcesoTitulacionService procesoTitulacionService;

    ProcesoTitulacionController(PersonaController personaController) {
        this.personaController = personaController;
    }

    // opbtiene los datos de la persona "logeada" a partir del email del token de
    // Keycloak
    @GetMapping(path = "/datos-personales", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonaLigeroDTO> obtenerDatosPersonales(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = jwt.getClaim("email");
        return ResponseEntity.ok(this.gestorPersonaServiceImpl.getDatosPersonaByEmail(email));
    }

    // busca una persona (rol-estudiante) a partir de su email para agregarlo a un
    // proceso de titulacion grupal
    @GetMapping(path = "/buscar-persona", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> agregarCompaniero(String email) {

        return new ResponseEntity<>(this.personaService.findByEmail(email), HttpStatus.OK);

    }

    // inserta un proceso de titulacion e inicializa todos los valores en las tablas
    // asociadas
    @PostMapping("/inscripcion")
    public ResponseEntity<String> inscribirProceso(@RequestBody ProcesoTitulacionDTO procesoTitulacionDTO) {
        System.out.println("inscipcion....... " + procesoTitulacionDTO.getTipoProceso());
        try {
            // Reutiliza la lógica del servicio para insertar un proceso de titulación
            this.gestorProcesoService.insert(procesoTitulacionDTO);
            return ResponseEntity.ok("Proceso de titulación creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el proceso: " + e.getMessage());
        }
    }

    @GetMapping(path = "/proceso/{id}/revisor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerDatosRevisorByProcesoId(@PathVariable(name = "id") Integer id) {

        return new ResponseEntity<>(this.procesoTitulacionService.buscarRevisorYNota(id), HttpStatus.OK);
    }

}
