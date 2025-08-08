package com.security.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.security.service.dto.PersonaDTO;

public interface IGestorUsurio {

    public List<PersonaDTO> createUser(List<PersonaDTO> personaDTO);

    public List<PersonaDTO> getUsers();

    public PersonaDTO findUserByCedula(String cedula);

    public Boolean updateUser(PersonaDTO personaDTO);

    public Boolean deleteUser(String idKeycloak);

    public Boolean insertarMasivo(MultipartFile personas);

    public PersonaDTO insertarIndividual(PersonaDTO personaDTO);
}
