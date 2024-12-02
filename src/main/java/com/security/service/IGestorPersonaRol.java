package com.security.service;

import com.security.db.Persona;
import com.security.service.dto.PersonaDTO;

public interface IGestorPersonaRol {

    public PersonaDTO insertar(Persona persona, String rol);

}
