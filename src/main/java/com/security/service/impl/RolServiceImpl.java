package com.security.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Rol;
import com.security.exception.CustomException;
import com.security.repo.IRolRepository;
import com.security.service.IRolService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolServiceImpl implements IRolService {

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
    public Optional<Rol> findByIdOptional(Integer id) {
        return this.rolRepository.findById(id);
    }

    @Override
    public Rol findById(Integer id) {
        return this.rolRepository.findById(id)
            .orElseThrow(() -> new CustomException("Rol: "+id +" no encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Rol> findAllByIds(List<Integer> ids) {
        return this.rolRepository.findAllById(ids);
    }

    @Override
    public List<Rol> findAll() {
        return this.rolRepository.findAll();
    }

    @Override
    public Rol update(Rol rol) {

        this.rolRepository.findById(rol.getId())
                .orElseThrow(() -> new EntityNotFoundException("Rol: "+rol.getId() +" no encontrado"));

        return this.rolRepository.save(rol);

    }

    @Override
    public List<Rol> findByNombreIn(List<String> names) {
        return this.rolRepository.findByNombreIn(names);
    }



}
