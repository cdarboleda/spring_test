package com.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Proceso;
import com.security.repo.IProcesoRepository;
import com.security.service.IProcesoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProcesoServiceImpl implements IProcesoService{

    @Autowired
    private IProcesoRepository procesoRepository;

    @Override
    public Proceso insert(Proceso proceso) {
        return this.procesoRepository.save(proceso);
    }

    @Override
    public Proceso findById(Integer id) {
        Proceso proceso = procesoRepository.findById(id)
        .orElseThrow(()-> new EntityNotFoundException("No hay proceso con id: " + id));

        return proceso;
    }

}