package com.security.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.impl.EmailPasoCompletado;
import com.security.service.impl.EmailPasoRechazado;
import com.security.service.impl.EmailProcesoCancelado;
import com.security.service.impl.EmailProcesoFinalizado;
import com.security.service.impl.EmailProcesoIniciado;

@RestController
@RequestMapping("/notificacion")
public class EmailController {

    @Autowired
    private EmailPasoCompletado emailPasoCompletado;

    @Autowired
    private EmailPasoRechazado emailPasoRechazado;

    @Autowired
    private EmailProcesoCancelado emailProcesoCancelado;

    @Autowired
    private EmailProcesoFinalizado emailProcesoFinalizado;

    @Autowired
    private EmailProcesoIniciado emailProcesoIniciado;

    // @PostMapping(path ="/paso/rechazado", produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public String notificacionPasoRechazado() {
    // return this.emailPasoRechazado.send(null, null);
    // // return this.emailPasoRechazado.send("vavabisga@gmail.com", "Subir
    // documentación del Docente", 45);
    // }

    // esto es del paso Anterior
    // responsableNombre, responsableCorreo, procesoId, tipoProceso, pasoNombre,
    // observaciones

    @PostMapping(path = "/paso/rechazado", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String notificacionPasoRechazado(@RequestBody Map<String, Object> data) {
        try {
            emailPasoRechazado.send(data);
            return "{\"mensaje\": \"Correo de rechazado enviado correctamente\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"mensaje\": \"No se pudo enviar el correo de rechazado. Ocurrió un error.\"}";
        }
    }

    @PostMapping(path = "/proceso/cancelado", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String notificacionProcesoCancelado(@RequestBody Map<String, Object> data) {
        try {
            emailProcesoCancelado.send(data);
            return "{\"mensaje\": \"Correo de cancelación enviado correctamente\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"mensaje\": \"No se pudo enviar el correo de cancelación. Ocurrió un error.\"}";
        }
    }

    @PostMapping(path = "/proceso/finalizado", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String notificacionProcesoFinalizado(@RequestBody Map<String, Object> data) {

        try {
            emailProcesoFinalizado.send(data);
            return "{\"mensaje\": \"Correo de finalización enviado correctamente\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"mensaje\": \"No se pudo enviar el correo de finalización. Ocurrió un error.\"}";
        }

    }

    @PostMapping(path = "/proceso/iniciado", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String notificacionProcesoIniciado(@RequestBody Map<String, Object> data) {
        try {
            emailProcesoIniciado.send(data);
            return "{\"mensaje\": \"Correo de inicialización enviado correctamente\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"mensaje\": \"No se pudo enviar el correo de inicialización. Ocurrió un error.\"}";
        }
    }

    @PostMapping(path = "/paso/completado", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String notificacionPasoCompletado(@RequestBody Map<String, Object> data) {
        try {
            emailPasoCompletado.send(data);
            return "{\"mensaje\": \"Correo de paso completado enviado correctamente\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"mensaje\": \"No se pudo enviar el correo de paso completado. Ocurrió un error.\"}";
        }
    }

}
