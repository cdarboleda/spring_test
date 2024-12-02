package com.security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Rol;
import com.security.repo.IRolRepository;
import com.security.service.IRolService;

@Service
public class RolServiceImpl implements IRolService{

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public Optional<Rol> buscarPorNombre(String nombre) {
        return this.rolRepository.findByNombre(nombre);
    }
    
}
