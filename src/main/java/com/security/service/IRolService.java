package com.security.service;

import java.util.List;
import java.util.Optional;

import com.security.db.Rol;

public interface IRolService {
    
    public Optional<Rol> buscarPorNombre(String nombre);

    public Rol insert(Rol rol);

    public Rol update(Rol rol);

    public Optional<Rol> findByIdOptional(Integer id);
    public Rol findById(Integer id);

    public List<Rol> findAll();
    public List<Rol> findAllByIds(List<Integer> ids);

    public List<Rol> findByNombreIn(List<String> names);
}
