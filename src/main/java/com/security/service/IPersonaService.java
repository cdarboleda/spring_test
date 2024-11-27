package com.security.service;

import java.util.List;

import com.security.db.Rol;

public interface IPersonaService {
    public List<Rol> findPersonRoles(Integer id);
}
