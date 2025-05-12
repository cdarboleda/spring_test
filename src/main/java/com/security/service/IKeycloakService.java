package com.security.service;

import java.util.List;


import com.security.service.dto.UserDTO;

public interface IKeycloakService {

    public String createUser(String username, String email, List<String> roles, String firstName, String lastName );

    public List<UserDTO> getUsers();

    public UserDTO findUserByUsername(String username);

    public Boolean updateUser(String userId, String email, List<String> rolesToAssign, String firstName, String lastName);
    public String updateUserDetails(String userId, String newEmail, String firstName, String lastName);
    public String updateUserRoles(String userId, List<String> newRoles);

    public Boolean deleteUser(String userId);
}