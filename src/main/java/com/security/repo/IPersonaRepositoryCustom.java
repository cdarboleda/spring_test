package com.security.repo;

import java.util.List;

import com.security.db.PersonaRol;

public interface IPersonaRepositoryCustom {
    
    public List<PersonaRol> findPersonRoles(Integer id);
}
