package com.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.exception.CustomException;
import com.security.service.IKeycloakService;
import com.security.service.dto.UserDTO;
import com.security.util.KeycloakProvider;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class KeycloakServiceImpl implements IKeycloakService {

    @Value("${keycloak.realm.name}")
    private String realmName;

    @Value("${keycloak.client.name}")
    private String clientName;

    @Autowired
    private KeycloakProvider keycloakProvider;

    private RealmResource getKeycloak() {
        return keycloakProvider.getKeycloak().realm(realmName);
    }

    private List<RoleRepresentation> getClientRoles() {
        return getKeycloak().clients().get(clientName).roles().list();
    }

    private List<RoleRepresentation> getUserClientRoles(String userId) {
        return getKeycloak().users().get(userId).roles().clientLevel(clientName).listEffective();
    }

    private void assignRolesToUser(String userId, List<RoleRepresentation> roles) {
        getKeycloak().users().get(userId).roles().clientLevel(clientName).add(roles);

    }

    private void removeRolesFromUser(String userId, List<RoleRepresentation> roles) {
        getKeycloak().users().get(userId).roles().clientLevel(clientName).remove(roles);
    }

    @Override
    public String createUser(String username, String email, List<String> rolesToAssign, String firstName,
            String lastName) {
        // Usar un Set para evitar roles duplicados
        // Set<String> keycloakRolesToAssign = mapRolesToKeycloakRoles(rolesToAssign);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);
        user.setEmailVerified(true);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("roles_info", List.of(String.join(",", rolesToAssign)));
        user.setAttributes(attributes);
        // Crear el usuario en Keycloak
        Response response = null;
        String userId = null;
        try {
            response = getKeycloak().users().create(user);

            if (response.getStatus() == 409) {
                throw new CustomException("El usuario: ¨" + username + "¨ o email: ¨" + email + "¨ ya se encuentran registrados  ", HttpStatus.CONFLICT);
            }

            if (response.getStatus() != 201) {
                throw new RuntimeException("Error al crear el usuario: " + response.getStatusInfo());
            }

            userId = response.getLocation().getPath()
                    .substring(response.getLocation().getPath().lastIndexOf("/") + 1);

            List<RoleRepresentation> roles = getClientRoles().stream()
                    .filter(role -> rolesToAssign.contains(role.getName()))
                    .collect(Collectors.toList());

            assignRolesToUser(userId, roles);

            // keycloakProvider.getKeycloak()
            //         .realm(realmName)
            //         .users()
            //         .get(userId)
            //         .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));

            return userId;

        } catch (Exception e) {
            if (userId != null) {
                try {
                    this.deleteUser(userId);
                } catch (Exception deleteEx) {
                    System.err.println("Error al intentar hacer rollback en Keycloak: " + deleteEx.getMessage());
                }
            }
            throw new RuntimeException("Error al crear el usuario en Keycloak en createUser de KeycloakService", e);
        } finally {
            if (response != null) {
                response.close();
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

    @Override
    public Boolean deleteUser(String userId) {
        try {
            getKeycloak().users().get(userId).remove();
            return true;
        } catch (NotFoundException e) { // Si ya no existe en Keycloak, lo consideramos eliminado
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            // return false;
            throw new CustomException("Error al eliminar el usuario en Keycloak.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Boolean updateUser(String userId, String username, String email, List<String> rolesToAssign) {
        System.out.println(userId + " " + email + " " + rolesToAssign.toString());
        try {
            String updateDetailsMessage = updateUserDetails(userId, username, email);
            System.out.println(" updateDetailsMessage " + updateDetailsMessage);

            String updateRolesMessage = updateUserRoles(userId, rolesToAssign);
            System.out.println(" updateRolesMessage " + updateRolesMessage);

            return true;
        } catch (Exception e) {
            throw new CustomException(
                    "Hubo un error al actualizar el usuario, por favor revise los datos.",
                    HttpStatus.CONFLICT);
        }
    }

    @Override
    public String updateUserDetails(String userId, String newUsername, String newEmail) {
        UserRepresentation user = getKeycloak().users().get(userId).toRepresentation();
        user.setUsername(newUsername);
        user.setEmail(newEmail);

        getKeycloak().users().get(userId).update(user);
        return "Detalles del usuario actualizados con éxito.";
    }

    @Override
    public String updateUserRoles(String userId, List<String> newRoles) {

        // Set<String> keycloakRolesToAssign = mapRolesToKeycloakRoles(newRoles);

        List<RoleRepresentation> availableRoles = getClientRoles();
        List<RoleRepresentation> rolesToAssign = availableRoles.stream()
                .filter(role -> newRoles.contains(role.getName()))
                .collect(Collectors.toList());

        List<RoleRepresentation> currentRoles = getUserClientRoles(userId);
        removeRolesFromUser(userId, currentRoles);
        assignRolesToUser(userId, rolesToAssign);

        return "Roles del usuario actualizados con éxito";
    }

    // private Set<String> mapRolesToKeycloakRoles(List<String> roles) {
    // Set<String> keycloakRoles = new HashSet<>();
    // for (String role : roles) {
    // if ("secretaria".equalsIgnoreCase(role)) {
    // keycloakRoles.add("administrador");
    // } else {
    // keycloakRoles.add("usuario");
    // }
    // }
    // return keycloakRoles;
    // }

}