package com.security.service.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.security.db.Persona;
import com.security.db.ProcesoTitulacion;

@Service
public class EmailProcesoService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTemplateBuilder templateBuilder;

    @Autowired
    private PersonaServiceImpl personaService;

    public void enviarNotificacionProcesoInciado(ProcesoTitulacion procesoEspecifico) {
        enviarCorreoProceso(procesoEspecifico, "ProcesoIniciado",
                "Se ha iniciado tu proceso de titulación");
    }

    public void enviarNotificacionProcesoFinalizado(ProcesoTitulacion procesoEspecifico) {
        enviarCorreoProceso(procesoEspecifico, "ProcesoFinalizado",
                "Se ha finalizado tu proceso de titulación");
    }

    public void enviarNotificacionProcesoCancelado(ProcesoTitulacion procesoEspecifico) {
        enviarCorreoProceso(procesoEspecifico, "ProcesoCancelado",
                "Se ha cancelado tu proceso de titulación");
    }

    private void enviarCorreoProceso(ProcesoTitulacion proceso, String plantilla, String asunto) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    List<Persona> personas = new ArrayList<>();
                    personas.add(proceso.getProceso().getRequiriente());
                    if (proceso.getGrupo()) {
                        Persona companiero = personaService.findById(proceso.getCompanieroId());
                        personas.add(companiero);
                    }
                    for (Persona req : personas) {
                        Map<String, Object> variables = templateBuilder.construirVariablesProceso(proceso, req,
                                plantilla);
                        emailService.sendEmail(plantilla, req.getCorreo(), asunto, variables);

                    }

                } catch (Exception e) {
                    throw new RuntimeException("Error al enviar correo de proceso: " + e.getMessage(), e);
                }
            }
        });
    }
}
