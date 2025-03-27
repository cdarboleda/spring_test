package com.security.service;

import java.util.List;

import com.security.db.Materia;
import com.security.service.dto.MateriaDTO;
import com.security.service.dto.MateriaTablaDTO;

public interface IMateriaService {
    public List<MateriaTablaDTO> findAll();

    public MateriaTablaDTO insert(MateriaDTO materia);

    public MateriaTablaDTO update(MateriaDTO materia);

    public Materia findById(Integer id);

    public void deleteById(Integer id);
}
