package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Materia;
import com.security.exception.CustomException;
import com.security.repo.IMateriaRepository;
import com.security.service.IMateriaService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private IMateriaRepository materiaRepository;

    @Override
    public List<Materia> findAll() {
        return this.materiaRepository.findAll();
    }

    @Override
    public Materia insert(Materia materia) {
        return this.materiaRepository.save(materia);
    }

    @Override
    public Materia update(Materia materia) {
        this.findById(materia.getId());
        return this.materiaRepository.save(materia);
    }

    @Override
    public Materia findById(Integer id) {
        return this.materiaRepository.findById(id)
                .orElseThrow(() -> new CustomException("Materia: " + id + " no encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteById(Integer id) {
        this.materiaRepository.deleteById(id);
    }

}
