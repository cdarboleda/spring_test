package com.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.security.service.dto.PasoDTO;

import jakarta.mail.MessagingException;

@Service
public class EmailPasoRechazado {

    private List<String> observaciones = null;
    private PasoDTO paso;
    private String maestria;
    private String materia;

    @Autowired
    private EmailService emailService;


    private String useSend(){
         Map<String, Object> variables = new HashMap<>();
            variables.put("pasoNombre", paso.getNombre());
            variables.put("procesoId", paso.getIdProceso());
            variables.put("tipoProceso", paso.getTipoProceso());
            variables.put("responsableNombre", paso.getResponsableNombre());
            variables.put("responsableApellido", paso.getResponsableApellido());
            variables.put("responsableCorreo", paso.getResponsableCorreo());
            variables.put("observaciones", observaciones);
            variables.put("maestria", maestria);
            variables.put("materia", materia);
        System.out.println("paso.responsableCorreo()"+ paso.getResponsableCorreo());
            try {
                this.emailService.sendEmail("PasoRechazado", paso.getResponsableCorreo(), "Proceso # "+paso.getIdProceso()+": Un paso ha sido rechazado", variables);
                return "Mensaje enviado con exito";
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                return "Error al enviar el correo: " + e.getMessage();
        }  
    }

    @Async
    public void sendFromBackend(PasoDTO paso, List<String> observaciones, String maestria, String materia) {
        this.paso = paso;
        this.observaciones = observaciones;
        this.materia = materia;
        this.maestria = maestria;
        this.useSend();
    }

    @Async
    public void send(Map<String, Object> data) {
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("procesoId", data.get("procesoId").toString());
            variables.put("materia", data.get("materia").toString());
            variables.put("maestria", data.get("maestria").toString());
            variables.put("fecha", data.get("fecha").toString());
            variables.put("pasoNombre", data.get("pasoNombre").toString());
            variables.put("responsableNombre", data.get("responsableNombre").toString());
            variables.put("responsableApellido", data.get("responsableApellido").toString());
            variables.put("responsableCorreo", data.get("responsableCorreo").toString());
            variables.put("observaciones", data.get("observaciones").toString().split(";"));

            emailService.sendEmail(
                    "PasoRechazado", // nombre del archivo Thymeleaf sin .html
                    data.get("responsableCorreo").toString(), // Puedes reemplazar con correo dinámico si aplica
                    "Proceso #" + data.get("procesoId").toString() + ": Un paso ha sido rechazado",
                    variables);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
