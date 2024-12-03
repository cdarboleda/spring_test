package com.security.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Rol;
import com.security.repo.IRolRepository;
import com.security.service.IRolService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RolServiceImpl implements IRolService{

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public Optional<Rol> buscarPorNombre(String nombre) {
        return this.rolRepository.findByNombre(nombre);
    }

    @Override
    public Rol insert(Rol rol) {
        return this.rolRepository.save(rol);
    }

    @Override
    public Optional<Rol> findById(Integer id) {
        return this.rolRepository.findById(id);
    }

    @Override
    public List<Rol> findAll() {
        return this.rolRepository.findAll();
    }
    

    @Override
    public Rol update(Rol rol) {
        
        this.rolRepository.findById(rol.getId())
        .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado para actualizar"));

        return this.rolRepository.save(rol);

    }

    
}
