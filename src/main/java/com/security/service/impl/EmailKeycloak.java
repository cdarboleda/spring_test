package com.security.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.security.util.KeycloakProvider;

@Service
public class EmailKeycloak {

    @Autowired
    private KeycloakProvider keycloakProvider;

    @Value("${keycloak.realm.name}")
    private String realmName;

    @Async
    public void enviarCorreoCambioPassword(String userId) {
        try {
            // Aquí usarías tu keycloakProvider también, inyectado
            keycloakProvider.getKeycloak()
                    .realm(realmName)
                    .users()
                    .get(userId)
                    .executeActionsEmail(Collections.singletonList("UPDATE_PASSWORD"));

            System.out.println("Correo de actualización de contraseña enviado a usuario: " + userId);
        } catch (Exception e) {
            System.err.println("Error al enviar correo de cambio de contraseña: " + e.getMessage());
        }
    }
}
