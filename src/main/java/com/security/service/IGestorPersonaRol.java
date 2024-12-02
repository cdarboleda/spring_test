package com.security.service;

import com.security.db.Persona;
import com.security.service.dto.PersonaDTO;

public interface IGestorPersonaRol {

    public Persona insertar(PersonaDTO persona);
    public Persona actualizar(PersonaDTO persona);

}
