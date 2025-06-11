package com.security.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class EmailPasoCompletado {

    @Autowired
    private EmailService emailService;

    @Async
    public void send(Map<String, Object> data) {

        try {

            Integer procesoId = Integer.parseInt(data.get("procesoId").toString());
            String responsableNombre = data.get("responsableNombre").toString();
            String responsableCorreo = data.get("responsableCorreo").toString();
            String responsableApellido = data.get("responsableApellido").toString();
            String pasoAnterior = data.get("pasoAnterior").toString();
            String pasoActual = data.get("pasoActual").toString();
            String fecha = data.get("fecha").toString();
            String materia = data.get("materia").toString();
            String maestria = data.get("maestria").toString();

            Map<String, Object> variables = new HashMap<>();
            variables.put("procesoId", procesoId);
            variables.put("responsableNombre", responsableNombre);
            variables.put("responsableApellido", responsableApellido);
            variables.put("responsableCorreo", responsableCorreo);
            variables.put("pasoAnterior", pasoAnterior);
            variables.put("pasoActual", pasoActual);
            variables.put("materia", materia);
            variables.put("maestria", maestria);
            variables.put("fecha", fecha);


            emailService.sendEmail(
                    "PasoCompletado", // nombre del archivo Thymeleaf sin .html
                    responsableCorreo, // Puedes reemplazar con correo dinámico si aplica
                    "Proceso #" + procesoId +": Se ha completado un paso",
                    variables);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
