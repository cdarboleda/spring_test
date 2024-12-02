package com.security.service;

import java.util.Optional;

import com.security.db.Rol;

public interface IRolService {
    
    public Optional<Rol> buscarPorNombre(String nombre);
}
