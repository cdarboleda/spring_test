package com.security.service;

import java.util.List;

import org.keycloak.representations.idm.UserRepresentation;

import com.security.service.dto.UserDTO;

public interface IKeycloakService {

    public String createUser(String username, String email, List<String> roles);

    public List<UserDTO> getUsers();

    public UserDTO findUserByUsername(String username);

    public Boolean updateUser(String userId, String username, String email, List<String> rolesToAssign);
    public String updateUserDetails(String userId, String newUsername, String newEmail);
    public String updateUserRoles(String userId, List<String> newRoles);

    public Boolean deleteUser(String userId);
}