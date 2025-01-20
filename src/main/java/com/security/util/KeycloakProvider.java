package com.security.util;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.stereotype.Component;

@Component
public class KeycloakProvider {

    private static final String SERVER_URL = "http://localhost:8080";
    private static final String REALM_NAME = "proyect-realm";
    private static final String REALM_MASTER = "master";
    private static final String ADMIN_CLI = "admin-cli";
    private static final String USER_CONSOLE = "admin";
    private static final String PASSWORD_CONSOLE = "admin";
    private static final String CLIENT_SECRET = "admin";

    public RealmResource getRealmResource() {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl(SERVER_URL)
            .realm(REALM_MASTER)
            .clientId(ADMIN_CLI)
            .username(USER_CONSOLE)
            .password(PASSWORD_CONSOLE)
            .clientSecret(CLIENT_SECRET)
            .resteasyClient(new ResteasyClientBuilderImpl()
                    .connectionPoolSize(10)
                    .build())
            .build();

            return keycloak.realm(REALM_NAME);
        } catch (Exception e) {
            System.out.println("error-------------------------------"+e);
            return null;
        }
        
    }

    public UsersResource getUserResource() {
        try {
            RealmResource realmResource = getRealmResource();
            return realmResource.users();  
        } catch (Exception e) {
            System.out.println("error-------------------------------"+e);
            return null;
        }

    }
}
