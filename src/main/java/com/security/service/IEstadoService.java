package com.security.service;

import java.util.List;

import com.security.db.Estado;

public interface IEstadoService {
    public Estado findById(Integer id);
    public List<Estado> findAll();
    public Estado insert(Estado estado);
    public Estado update(Estado estado);
    public void delete(Integer id);
    
}
