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
        System.out.println("paso.responsableCorreo()"+ paso.getResponsableCorreo());
            try {
                this.emailService.sendEmail("PasoRechazado", paso.getResponsableCorreo(), "Proceso # "+paso.getIdProceso()+": Un paso ha sido rechazado", variables);
                // this.emailService.sendEmail("PasoRechazado", "kpchiguano@uce.edu.ec", "Proceso "+paso.getTipoProceso()+" # "+paso.getIdProceso()+": Un paso ha sido rechazado", variables);
                return "Mensaje enviado con exito";
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                return "Error al enviar el correo: " + e.getMessage();
        }  
    }

    @Async
    public void send(PasoDTO paso, List<String> observaciones){
        this.paso = paso;
        System.out.println("el paso: "+paso );
        this.observaciones = observaciones;
        this.useSend();
    }

}
