package com.security.util;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakProvider {


    private final Keycloak keycloak;

    public KeycloakProvider(@Value("${server.url}") String serverUrl,
            @Value("${realm.master}") String realmMaster,
            @Value("${admin.cli}") String adminCli,
            @Value("${user.console}") String userConsole,
            @Value("${password.console}") String passwordConsole) {


        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realmMaster) // Siempre se autentica en el realm "master"
                .clientId(adminCli)
                .username(userConsole)
                .password(passwordConsole)
                .build();
    }

    public Keycloak getKeycloak() {
        return keycloak;
    }
}
