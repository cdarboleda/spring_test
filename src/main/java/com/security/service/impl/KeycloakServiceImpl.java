package com.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.security.service.IKeycloakService;
import com.security.service.dto.UserDTO;
import com.security.util.KeycloakProvider;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KeycloakServiceImpl implements IKeycloakService {

    private static final String REALM_NAME = "proyect-realm";
    private static final String CLIENT_NAME = "2916a8a6-f355-412a-9d4b-84cce416fe43";

    @Autowired
    private KeycloakProvider keycloakProvider;

    private RealmResource getKeycloak() {
        return keycloakProvider.getKeycloak().realm(REALM_NAME);
    }

    private List<RoleRepresentation> getClientRoles() {
        return getKeycloak().clients().get(CLIENT_NAME).roles().list();
    }

    private List<RoleRepresentation> getUserClientRoles(String userId) {
        return getKeycloak().users().get(userId).roles().clientLevel(CLIENT_NAME).listEffective();
    }

    private void assignRolesToUser(String userId, List<RoleRepresentation> roles) {
        getKeycloak().users().get(userId).roles().clientLevel(CLIENT_NAME).add(roles);
    }

    private void removeRolesFromUser(String userId, List<RoleRepresentation> roles) {
        getKeycloak().users().get(userId).roles().clientLevel(CLIENT_NAME).remove(roles);
    }

    @Override
    public String createUser(String username, String email, List<String> rolesToAssign) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);
        user.setEmailVerified(true);

        // Crear el usuario en Keycloak
        Response response = null;

        try {
            response = getKeycloak().users().create(user);

            if (response.getStatus() != 201) {
                // throw new CustomException("Error keycloak: ",
                // HttpStatus.valueOf(response.getStatus()));
                // System.out.println("Error al crear el usuario: " + response.getStatus());
                return null;
            }

            String userId = response.getLocation().getPath()
                    .substring(response.getLocation().getPath().lastIndexOf("/") + 1);

            List<RoleRepresentation> roles = getClientRoles().stream()
                    .filter(role -> rolesToAssign.contains(role.getName()))
                    .collect(Collectors.toList());

            assignRolesToUser(userId, roles);

            // Enviar correo para que el usuario configure su contraseña
            // keycloakProvider.getKeycloak()
            // .realm(REALM_NAME)
            // .users()
            // .get(userId)
            // .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));

            return userId;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (response != null) {
                response.close(); // Cerrar la respuesta para evitar fugas
            }
        }

    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserRepresentation> users = getKeycloak().users().list();
        return users.stream().map(user -> {
            List<String> roleNames = getUserClientRoles(user.getId()).stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());

            return UserDTO.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .roles(roleNames)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        List<UserRepresentation> users = getKeycloak().users().search(username, true);

        if (users.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con username: " + username);
        }

        UserRepresentation user = users.get(0);
        List<String> roleNames = getUserClientRoles(user.getId()).stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());

        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleNames)
                .build();
    }

    public Boolean updateUser(String userId, String username, String email, List<String> rolesToAssign) {
        System.out.println(userId + " " + email + " " + rolesToAssign.toString());
        try {
            String updateDetailsMessage = updateUserDetails(userId, username, email);
            System.out.println(updateDetailsMessage);

            String updateRolesMessage = updateUserRoles(userId, rolesToAssign);
            System.out.println(updateRolesMessage);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteUser(String userId) {
        try {
            getKeycloak().users().get(userId).remove();
            return true;
        } catch (NotFoundException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String updateUserDetails(String userId, String newUsername, String newEmail) {
        UserRepresentation user = getKeycloak().users().get(userId).toRepresentation();
        user.setUsername(newUsername);
        user.setEmail(newEmail);

        getKeycloak().users().get(userId).update(user);
        return "Detalles del usuario actualizados con éxito";
    }

    @Override
    public String updateUserRoles(String userId, List<String> newRoles) {
        List<RoleRepresentation> availableRoles = getClientRoles();
        List<RoleRepresentation> rolesToAssign = availableRoles.stream()
                .filter(role -> newRoles.contains(role.getName()))
                .collect(Collectors.toList());

        List<RoleRepresentation> currentRoles = getUserClientRoles(userId);
        removeRolesFromUser(userId, currentRoles);
        assignRolesToUser(userId, rolesToAssign);

        return "Roles del usuario actualizados con éxito";
    }

}
