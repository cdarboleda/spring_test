package com.security.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.security.service.IGestorProcesoService;
import com.security.service.IGestorProcesoTitulacionService;
import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.ProcesoTitulacionDTO;
import com.security.service.dto.ProcesoTitulacionLigeroDTO;
import com.security.service.dto.ProcesoCompletoTitulacionDTO;
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
        private IGestorProcesoService gestorProcesoService;

        @Autowired
        private IProcesoTitulacionService procesoTitulacionService;

        @Autowired
        private IGestorProcesoTitulacionService gestorProcesoTitulacionService;

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
        public ResponseEntity<?> obtenerResponsablePaso(@PathVariable(name = "id") Integer id,
                        @PathVariable(name = "nombre") String nombre) {
                return new ResponseEntity<>(this.gestorProcesoTitulacionService.buscarResponsablePaso(id, nombre),
                                HttpStatus.OK);
        }

        @GetMapping(path = "/{id}/tutor", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> obtenerDatosTutorTitulacion(@PathVariable(name = "id") Integer id) {
                return new ResponseEntity<>(this.procesoTitulacionService.buscarTutorProcesoTitulacion(id),
                                HttpStatus.OK);
        }

        @GetMapping(path = "/cantidad-procesos/tutor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> obtenerCantidadProcesosTitulacionActivos(@PathVariable(name = "id") Integer id) {
                return new ResponseEntity<>(this.procesoTitulacionService.obtenerCantidadProcesosTitulacionTutor(id),
                                HttpStatus.OK);
        }

}
