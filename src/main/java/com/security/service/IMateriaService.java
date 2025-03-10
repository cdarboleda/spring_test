package com.security.service;

import java.util.List;

import com.security.db.Materia;

public interface IMateriaService {
    public List<Materia> findAll();

    public Materia insert(Materia materia);

    public Materia update(Materia materia);

    public Materia findById(Integer id);

    public void deleteById(Integer id);
}
