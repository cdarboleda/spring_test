package com.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.security.db.Persona;
import com.security.service.dto.PasoDTO;

import jakarta.mail.MessagingException;

@Service
public class EmailProcesoCancelado {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PersonaServiceImpl personaService;

    @Async
    public void send(Map<String, Object> data) {

        try {

            Persona persona = new Persona();

            persona = this.personaService.findById(Integer.parseInt(data.get("requirienteId").toString()));

            Long procesoId = Long.parseLong(data.get("procesoId").toString());
            String requirienteNombre = persona.getNombre();
            String requirienteApellido = persona.getApellido();
            String requirienteCedula = persona.getCedula();
            String fecha = data.get("fecha").toString();
            String[] motivo = data.get("motivo").toString().split(";");
            String materia = data.get("materia").toString();
            String maestria = data.get("maestria").toString();

            Map<String, Object> variables = new HashMap<>();
            variables.put("procesoId", procesoId);
            variables.put("requirienteNombre", requirienteNombre);
            variables.put("requirienteApellido", requirienteApellido);
            variables.put("requirienteCedula", requirienteCedula);
            variables.put("materia", materia);
            variables.put("maestria", maestria);
            variables.put("fecha", fecha);
            variables.put("motivo", motivo);

            emailService.sendEmail(
                    "ProcesoCancelado", // nombre del archivo Thymeleaf sin .html
                    persona.getCorreo(), // Puedes reemplazar con correo dinámico si aplica
                    "Cancelación del Proceso #" + procesoId,
                    variables);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
