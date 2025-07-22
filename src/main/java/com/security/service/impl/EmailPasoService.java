package com.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.ProcesoTitulacion;
import com.security.db.enums.PasoTitulacion;
import com.security.exception.CustomException;
import com.security.repo.IProcesoTitulacionRepository;
import com.security.service.dto.ProcesoTitulacionDTO;
import com.security.service.dto.utils.ConvertidorProceso;

import jakarta.mail.MessagingException;

@Service
public class EmailPasoService {

    @Autowired
    private PersonaServiceImpl personaServiceImpl;

    @Autowired
    private ConvertidorProceso convertidorProceso;

    @Autowired
    private IProcesoTitulacionRepository procesoTitulacionServiceImpl;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTemplateBuilder templateBuilder;

    @Autowired
    private PasoServiceImpl pasoService;

    public void enviarNotificacionPaso(Paso paso) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    notificar(paso);
                } catch (Exception e) {
                    throw new CustomException("Error al enviar correo: " + e.getMessage(), HttpStatus.CONFLICT);
                }
            }
        });
    }

    public void notificar(Paso paso) {
        String estado = paso.getDescripcionEstado();
        String nombrePaso;

        try {
            // nombrePaso = PasoNombre.valueOf(paso.getNombre().toUpperCase());zsx
            nombrePaso = PasoTitulacion.fromString(paso.getNombre()).getNombre();

            if (nombrePaso == null) {
                throw new CustomException("Paso no encontrado con nombre: " + paso.getNombre(), HttpStatus.NOT_FOUND);
            }

        } catch (IllegalArgumentException e) {
            throw new CustomException("Paso no encontrado con nombre: " + paso.getNombre(), HttpStatus.NOT_FOUND);
        }

        switch (nombrePaso.toUpperCase()) {
            case "REVISION_DOCUMENTACION", "REVISION_IDONEIDAD", "GENERACION_REPORTE_ANTI_PLAGIO" -> {
                System.out.println("entro en rechazado --  estado:- " + estado);

                if (estado.equalsIgnoreCase("Rechazado")) {
                    notificarRechazo(paso);
                }
            }
            case "DESARROLLO_PROYECTO", "POSTULACION" -> {
                System.out.println("entro en en curso --  estado:- " + estado);
                if (estado.equalsIgnoreCase("En curso")) {
                    notificarEnCurso(paso);
                }
            }
        }
    }

    private void notificarEnCurso(Paso paso) {
        ProcesoTitulacion proceso = procesoTitulacionServiceImpl.findById(paso.getProceso().getId()).get();

        List<Persona> personas = obtenerPersonasInvolucradas(proceso, paso.getResponsable());

        Paso pasoAnterior = pasoService.findByIdOptional(paso.getId() - 1)
                .orElseThrow(() -> new CustomException("No se encontró el paso anterior", HttpStatus.NOT_FOUND));

        Map<String, Object> vars = templateBuilder.construirVariablesPaso(paso);
        vars.put("pasoAnterior", pasoAnterior.getNombre());
        vars.put("pasoActual", paso.getNombre());

        enviarCorreoAPersonas(personas, vars, "PasoEnCurso",
                "Se ha completado el paso " + pasoAnterior.getNombre());
    }

    private void notificarRechazo(Paso paso) {

        ProcesoTitulacion proceso = procesoTitulacionServiceImpl.findById(paso.getProceso().getId()).get();

        List<Persona> personas = obtenerPersonasInvolucradas(proceso, paso.getProceso().getRequiriente());

        Map<String, Object> vars = templateBuilder.construirVariablesPaso(paso);
        vars.put("pasoNombre", paso.getNombre());
        vars.put("observaciones", paso.getObservacion());

        enviarCorreoAPersonas(personas, vars, "PasoRechazado",
                "Se ha rechazado el paso " + paso.getNombre());
    }

    private List<Persona> obtenerPersonasInvolucradas(ProcesoTitulacion proceso, Persona responsable) {
        List<Persona> personas = new ArrayList<>();
        personas.add(responsable);

        if (Boolean.TRUE.equals(proceso.getGrupo())) {
            Persona companiero = personaServiceImpl.findById(proceso.getCompanieroId());
            personas.add(companiero);
        }

        if (proceso.getTutorId() != null) {
            Persona tutor = personaServiceImpl.findById(proceso.getTutorId());
            personas.add(tutor);
        }
        for (Persona persona : personas) {
                
        }

        return personas;
    }

    private void enviarCorreoAPersonas(List<Persona> personas, Map<String, Object> vars,
            String plantilla, String asuntoBase) {

        for (Persona persona : personas) {

            System.out.println("Nombre Responsable: " + persona.getNombre() + " "
                    + persona.getApellido() + " Correo: " + persona.getCorreo());
            Map<String, Object> varsPersona = new HashMap<>(vars);
            varsPersona.put("responsableNombre", persona.getNombre());
            varsPersona.put("responsableApellido", persona.getApellido());
            try {
                emailService.sendEmail(
                        plantilla,
                        persona.getCorreo(),
                        asuntoBase,
                        varsPersona);
            } catch (MessagingException e) {

                throw new CustomException("Error al enviar correo: " + e.getMessage(), HttpStatus.CONFLICT);
            }
        }
    }


    // public void enviarNotificacionPaso(Paso paso) {
    // TransactionSynchronizationManager.registerSynchronization(new
    // TransactionSynchronization() {
    // @Override
    // public void afterCommit() {
    // String estado = paso.getDescripcionEstado();
    // boolean pasoEnviarNotificacionEmal = paso.getOrden() == 2
    // || paso.getOrden() == 4
    // || paso.getOrden() == 13;
    // try {

    // // hacer un case con el nombre del paso y ejecutar las acciones
    // correspondientes
    // // de acuerdo al paso (ya que aveces se requiere enviar a una persona tercera
    // no
    // // al responsable del siguiente ni el anterior)
    // // ya que cada paso tiene un regla
    // // if (estado.equalsIgnoreCase("Completado") && pasoEnviarNotificacionEmal) {
    // // enviarEmailPasoEnCurso(paso);
    // // } else if (estado.equalsIgnoreCase("Rechazado") &&
    // // pasoEnviarNotificacionEmal) {
    // // enviarEmailPasoRechazado(paso);
    // // }
    // System.out.println("paso.getNombre()-------: " + paso.getNombre());
    // switch (paso.getNombre()) {
    // case "revision_documentacion": // se envia al requiriente que se rechazo la
    // documentacion
    // if (estado.equalsIgnoreCase("Rechazado")) {
    // enviarEmailPasoRechazado(paso); // validar para grupal
    // }
    // break;
    // case "revision_idoneidad": // se envia al requiriente que se rechazo el plan
    // if (estado.equalsIgnoreCase("Rechazado")) {
    // enviarEmailPasoRechazado(paso); // validar para grupal y enviar al paso 1
    // }
    // break;
    // // envia al requiriente, grupo(si aplica), tutor que ya aprobó la lectoria
    // case "desarrollo_proyecto":
    // if (estado.equalsIgnoreCase("En curso")) {
    // enviarEmailPasoEnCurso(paso);
    // }
    // break;
    // case "generacion_reporte_anti_plagio": // se envia al requirietne y al tutor
    // que se rechazo
    // if (estado.equalsIgnoreCase("Rechazado")) {
    // enviarEmailPasoRechazado(paso); // validar para grupal y enviar al tutor
    // }
    // break;
    // // envia al requiriente, grupo(si aplica), tutor que ya aprobó la lectoria
    // case "postulacion_defensa":
    // if (estado.equalsIgnoreCase("En curso")) {
    // enviarEmailPasoEnCurso(paso);
    // }
    // break;

    // }

    // } catch (Exception e) {
    // throw new CustomException("Error al enviar correo: " + e.getMessage(),
    // HttpStatus.CONFLICT);
    // }
    // }
    // });

    // }

    // private void enviarEmailPasoEnCurso(Paso paso) {
    // Map<String, Object> vars = templateBuilder.construirVariablesPaso(paso);
    // ProcesoTitulacion procesoTitulacion = (ProcesoTitulacion) convertidorProceso
    // .convertirACompletoDTO(paso.getProceso());
    // List<Persona> listaPersonas = new ArrayList<>();
    // listaPersonas.add(paso.getResponsable());
    // if (procesoTitulacion.getGrupo()) {
    // Persona companiero =
    // this.personaServiceImpl.findById(procesoTitulacion.getCompanieroId());
    // listaPersonas.add(companiero);
    // }
    // if (procesoTitulacion.getTutorId() != null) {
    // Persona tutor =
    // this.personaServiceImpl.findById(procesoTitulacion.getTutorId());
    // listaPersonas.add(tutor);
    // }
    // Optional<Paso> pasoAnterior = pasoService.findByIdOptional(paso.getId() - 1);
    // vars.put("pasoAnterior", pasoAnterior.get().getNombre());
    // vars.put("pasoActual", paso.getNombre());
    // try {

    // for (Persona persona : listaPersonas) {
    // vars.put("responsableNombre", persona.getNombre());
    // vars.put("responsableApellido", persona.getApellido());

    // emailService.sendEmail(
    // "PasoEnCurso",
    // persona.getCorreo(),
    // "Se ha completado el paso " + pasoAnterior.get().getNombre(),
    // vars);
    // }

    // } catch (MessagingException e) {
    // throw new CustomException("Error al enviar correo: " + e.getMessage(),
    // HttpStatus.CONFLICT);
    // }

    // }

    // private void enviarEmailPasoRechazado(Paso paso) {
    // Map<String, Object> vars = templateBuilder.construirVariablesPaso(paso);
    // Optional<Paso> pasoAnterior = pasoService.findByIdOptional(paso.getId() - 1);
    // List<Persona> listaPersonas = new ArrayList<>();
    // listaPersonas.add(pasoAnterior.get().getResponsable());

    // ProcesoTitulacion procesoTitulacion = (ProcesoTitulacion) convertidorProceso
    // .convertirACompletoDTO(paso.getProceso());
    // if (procesoTitulacion.getGrupo()) {
    // Persona companiero =
    // this.personaServiceImpl.findById(procesoTitulacion.getCompanieroId());
    // listaPersonas.add(companiero);
    // }
    // if (procesoTitulacion.getTutorId() != null) {
    // Persona tutor =
    // this.personaServiceImpl.findById(procesoTitulacion.getTutorId());
    // listaPersonas.add(tutor);
    // }
    // vars.put("pasoNombre", paso.getNombre());
    // vars.put("observaciones", paso.getObservacion());

    // try {
    // for (Persona p : listaPersonas) {
    // vars.put("responsableNombre", p.getNombre());
    // vars.put("responsableApellido", p.getApellido());
    // emailService.sendEmail(
    // "PasoRechazado",
    // p.getCorreo(),
    // "Se ha rechazado el paso " + paso.getNombre(),
    // vars);
    // }

    // } catch (MessagingException e) {
    // throw new CustomException("Error al enviar correo: " + e.getMessage(),
    // HttpStatus.CONFLICT);
    // }

    // }
}
