package com.security.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.IKeycloakService;
import com.security.service.dto.UserDTO;

@RestController
@RequestMapping("/keycloak/user")
@PreAuthorize("hasAnyRole('administrador', 'secretaria')") // quitar rol secretaria (4/11/20205)
public class KeycloakController {

    @Autowired
    private IKeycloakService iKeycloakService;

    @GetMapping("/search")
    public List<?> getUsers() {
        return iKeycloakService.getUsers();
    }

    @PostMapping("/create")
    public String createUser(@RequestBody UserDTO dto) {
        return iKeycloakService.createUser(dto.getUsername(), dto.getEmail(), dto.getRoles());
    }

}
