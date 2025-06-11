package com.security.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.security.db.Persona;

import jakarta.mail.MessagingException;

@Service
public class EmailProcesoIniciado {

    @Autowired
    private EmailService emailService;

    @Async
    public void send(Map<String, Object> data) {

        try {

            Integer procesoId = Integer.parseInt(data.get("procesoId").toString());
            String requirienteNombre = data.get("requirienteNombre").toString();
            String requirienteCorreo = data.get("requirienteCorreo").toString();
            String requirienteApellido = data.get("requirienteApellido").toString();
            String fecha = data.get("fecha").toString();
            String materia = data.get("materia").toString();
            String maestria = data.get("maestria").toString();

            Map<String, Object> variables = new HashMap<>();
            variables.put("procesoId", procesoId);
            variables.put("requirienteNombre", requirienteNombre);
            variables.put("requirienteApellido", requirienteApellido);
            variables.put("materia", materia);
            variables.put("maestria", maestria);
            variables.put("fecha", fecha);


            emailService.sendEmail(
                    "ProcesoIniciado", // nombre del archivo Thymeleaf sin .html
                    requirienteCorreo, // Puedes reemplazar con correo dinámico si aplica
                    "Proceso #" + procesoId +" ha sido iniciado",
                    variables);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
