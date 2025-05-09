package com.security.controller;

import java.time.LocalDateTime;
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
import com.security.service.IGestorProcesoTitulacionService;
import com.security.service.IPersonaService;
import com.security.service.IProcesoService;
import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.PersonaLigeroDTO;
import com.security.service.dto.ProcesoTitulacionDTO;
import com.security.service.dto.ProcesoTitulacionLigeroDTO;
import com.security.service.dto.TitulacionResponsableNotaLigeroDTO;
import com.security.service.dto.PersonaTitulacionLigeroDTO;
import com.security.service.dto.ProcesoCompletoTitulacionDTO;
import com.security.service.dto.ProcesoDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/titulacion")
public class ProcesoTitulacionController {

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

        @Autowired
        private IGestorProcesoTitulacionService gestorProcesoTitulacionService;

        // opbtiene los datos de la persona "logeada" a partir del email del token de
        // Keycloak
        @GetMapping(path = "/datos-personales", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<PersonaLigeroDTO> obtenerDatosPersonales(Authentication authentication) {
                Jwt jwt = (Jwt) authentication.getPrincipal();

                String email = jwt.getClaim("email");
                return ResponseEntity.ok(this.gestorPersonaServiceImpl.getDatosPersonaByEmail(email));
        }

        @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> actualizarProcesoTitulacion(@PathVariable(name = "id") Integer id,
                        @RequestBody ProcesoTitulacionLigeroDTO procesoTitulacionDTO) {

                return new ResponseEntity<>(
                                this.procesoTitulacionService.actualizarProcesoTitulacion(id, procesoTitulacionDTO),
                                HttpStatus.OK);
        }

        @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> obtenerProcesoTitulacion(@PathVariable(name = "id") Integer id) {
                return new ResponseEntity<>(this.procesoTitulacionService.obtenerProcesoTitulacion(id),
                                HttpStatus.OK);
        }

        // busca una persona (rol-estudiante) a partir de su email para agregarlo a un
        // proceso de titulacion grupal
        // @GetMapping(path = "/buscar-persona", produces =
        // MediaType.APPLICATION_JSON_VALUE)
        // public ResponseEntity<?> agregarCompaniero(String email) {

        // return new ResponseEntity<>(this.personaService.findByEmail(email),
        // HttpStatus.OK);

        // }

        // inserta un proceso de titulacion e inicializa todos los valores en las tablas
        // asociadas
        @PostMapping("/inscripcion")
        public ResponseEntity<String> inscribirProceso(@RequestBody ProcesoTitulacionDTO procesoTitulacionDTO) {
                System.out.println("inscipcion....... " + procesoTitulacionDTO);
                try {
                        // Reutiliza la lógica del servicio para insertar un proceso de titulación
                        ProcesoCompletoTitulacionDTO proceso = (ProcesoCompletoTitulacionDTO) this.gestorProcesoService
                                        .insert(procesoTitulacionDTO);
                        this.procesoTitulacionService.asignarSecretariaAlproceso(proceso);
                        return ResponseEntity.ok("Proceso de titulación creado con éxito.");
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("Error al crear el proceso: " + e.getMessage());
                }
        }

        @GetMapping(path = "/{id}/paso/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> obtenerResponsableNotaPasoEspecifico(@PathVariable(name = "id") Integer id,
                        @PathVariable(name = "nombre") String nombre) {
                return new ResponseEntity<>(this.gestorProcesoTitulacionService.buscarResponsableNotaPaso(id, nombre),
                                HttpStatus.OK);
        }

        @PostMapping(path = "/{id}/calificacion", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> insertarCalificacionPasoEspecifico(
                        @PathVariable Integer id,
                        @RequestBody TitulacionResponsableNotaLigeroDTO responsableNotaLigeroDTO) {

                this.procesoTitulacionService.insertarNotaPasoEspecifico(id, responsableNotaLigeroDTO);
                return ResponseEntity
                                .ok("El proceso de Tituacion con id: " + id + " se guardo correctamente en el paso: "
                                                + responsableNotaLigeroDTO.getNombrePaso());
        }

        @GetMapping(path = "/{id}/tutor", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> obtenerDatosTutorTitulacion(@PathVariable(name = "id") Integer id) {
                return new ResponseEntity<>(this.procesoTitulacionService.buscarTutorProcesoTitulacion(id),
                                HttpStatus.OK);
        }

        @PostMapping(path = "/{id}/tutor", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> insertarTutorTitulacion(@PathVariable Integer id,
                        @RequestBody PersonaDTO personaTutorDTO) {

                this.procesoTitulacionService.agregarTutorProcesoTitulacion(id, personaTutorDTO);
                return ResponseEntity
                                .ok("El tutor: " + personaTutorDTO.getNombre() + " " + personaTutorDTO.getApellido()
                                                + " se insertor correctamente en el proceso de Tituacion con id: "
                                                + id);
        }

        @GetMapping(path = "/{id}/tribunal", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> obtenerPersonaTribunalUno(@PathVariable(name = "id") Integer id) {
                return new ResponseEntity<>(this.procesoTitulacionService.buscarPersonasTribunal(id),
                                HttpStatus.OK);
        }

        @PostMapping(path = "/{id}/tribunal-uno", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> insertarPersonaTribunalUno(@PathVariable Integer id,
                        @RequestBody PersonaDTO personaTribunalUnoDto) {

                this.procesoTitulacionService.agregarPersonaTribunaUno(id, personaTribunalUnoDto);
                return ResponseEntity
                                .ok("La persona: " + personaTribunalUnoDto.getNombre() + " "
                                                + personaTribunalUnoDto.getApellido()
                                                + " se insertor correctamente en el proceso de Tituacion con id: "
                                                + id);
        }

        @PostMapping(path = "/{id}/tribunal-dos", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> insertarPersonaTribunalDos(@PathVariable Integer id,
                        @RequestBody PersonaDTO personaTribunalDosDto) {

                this.procesoTitulacionService.agregarPersonaTribunalDos(id, personaTribunalDosDto);
                return ResponseEntity
                                .ok("La persona: " + personaTribunalDosDto.getNombre() + " "
                                                + personaTribunalDosDto.getApellido()
                                                + " se insertor correctamente en el proceso de Tituacion con id: "
                                                + id);
        }

        @PutMapping(path = "/{id}/fecha-defensa/{fecha}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> actualizarFechaDefensa(@PathVariable(name = "id") Integer id,
                        @PathVariable(name = "fecha") LocalDateTime fechaDefensa) {
                this.procesoTitulacionService.actualizarFechaDefensa(id, fechaDefensa);
                return ResponseEntity
                                .ok("La fecha de defensa se actualizo correctamente en el proceso de Tituacion con id: "
                                                + id);
        }

        @GetMapping(path = "/{id}/fecha-defensa", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> obtenerFechaDefensa(@PathVariable(name = "id") Integer id) {
                return new ResponseEntity<>(this.procesoTitulacionService.buscarFechaDefensa(id),
                                HttpStatus.OK);
        }

}
