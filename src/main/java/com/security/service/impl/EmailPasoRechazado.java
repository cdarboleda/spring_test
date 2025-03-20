package com.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.security.controller.EmailController;
import com.security.repo.ICarpetaDocumentoRepository;
import com.security.service.dto.PasoDTO;

import jakarta.mail.MessagingException;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
            variables.put("responsableEmail", paso.getResponsableEmail());
            variables.put("observaciones", observaciones);

            try {
                // this.emailService.sendEmail("PasoRechazado", paso.getResponsableEmail(), "Proceso # "+paso.getIdProceso()+": Un paso ha sido rechazado", variables);
                this.emailService.sendEmail("PasoRechazado", "kpchiguano@uce.edu.ec", "Proceso "+paso.getTipoProceso()+" # "+paso.getIdProceso()+": Un paso ha sido rechazado", variables);
                return "Mensaje enviado con exito";
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                return "Error al enviar el correo: " + e.getMessage();
        }  
    }

    @Async
    public void send(PasoDTO paso, List<String> observaciones){
        this.paso = paso;
        this.observaciones = observaciones;
        this.useSend();
    }

}
