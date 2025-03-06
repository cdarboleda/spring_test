package com.security.service;

import java.util.List;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Rol;
import com.security.service.dto.PersonaDTO;
import com.security.service.dto.PersonaLigeroDTO;

public interface IGestorPersonaService {

    public PersonaDTO insertar(PersonaDTO persona);
    public Persona actualizar(PersonaDTO persona);
    public List<Rol> findRolesByPersonaId(Integer id);
    public List<Paso> findPasosByPersonaId(Integer id);
    public List<PersonaLigeroDTO> findAllWithRoles();
    public void anadirPaso(Integer idPersona, Integer idPaso);
    public List<Persona> findPersonasByRol(String rol);
}
