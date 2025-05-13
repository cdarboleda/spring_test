package com.security.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.security.db.Persona;

import jakarta.mail.MessagingException;

@Service
public class EmailProcesoFinalizado {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PersonaServiceImpl personaService;

    @Async
    public void send(Map<String, Object> data) {
        try {
            Persona persona = this.personaService.findById(Integer.parseInt(data.get("requirienteId").toString()));

            Long procesoId = Long.parseLong(data.get("procesoId").toString());
            String requirienteNombre = persona.getNombre();
            String requirienteApellido = persona.getApellido();
            String requirienteCedula = persona.getCedula();
            String fecha = data.get("fecha").toString();
            String materia = data.get("materia").toString();
            String maestria = data.get("maestria").toString();

            Map<String, Object> variables = new HashMap<>();
            variables.put("procesoId", procesoId);
            variables.put("requirienteNombre", requirienteNombre);
            variables.put("requirienteApellido", requirienteApellido);
            variables.put("requirienteCedula", requirienteCedula);
            variables.put("fecha", fecha);
            variables.put("materia", materia);
            variables.put("maestria", maestria);

            emailService.sendEmail(
                "ProcesoFinalizado", // archivo Thymeleaf sin extensión
                persona.getCorreo(),
                "Proceso Finalizado #"+procesoId,
                variables
            );

        } catch (MessagingException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
