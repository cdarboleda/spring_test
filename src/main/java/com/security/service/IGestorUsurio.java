package com.security.service;

import java.util.List;

import com.security.service.dto.PersonaDTO;

public interface IGestorUsurio {

    public PersonaDTO createUser(PersonaDTO personaDTO);

    public List<PersonaDTO> getUsers();

    public PersonaDTO findUserByCedula(String cedula);

    public Boolean updateUser(PersonaDTO personaDTO);

    public Boolean deleteUser(String idKeycloak);
    
}
