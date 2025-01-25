package com.security.util;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.stereotype.Component;

@Component
public class KeycloakProvider {

    private static final String SERVER_URL = "http://localhost:8080";
    private static final String REALM_MASTER = "master";
    private static final String ADMIN_CLI = "admin-cli";
    private static final String USER_CONSOLE = "admin";
    private static final String PASSWORD_CONSOLE = "admin";

    private final Keycloak keycloak;

    public KeycloakProvider() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM_MASTER) // Siempre se autentica en el realm "master"
                .clientId(ADMIN_CLI)
                .username(USER_CONSOLE)
                .password(PASSWORD_CONSOLE)
                .build();
    }

    public Keycloak getKeycloak() {
        return keycloak;
    }
}
