package com.security.service;

import java.util.List;

import org.keycloak.representations.idm.UserRepresentation;

import com.security.service.dto.UserDTO;

public interface IKeycloakService {

    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> searchUserByUsername(String username);
    String createUser(UserDTO userDTO);
    void deleteUser(String userId);
    void updateUser(String userId, UserDTO userDTO);
}