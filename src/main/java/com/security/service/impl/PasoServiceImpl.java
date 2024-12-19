package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.factory.PasoFactoryManager;
import com.security.repo.IPasoRepository;
import com.security.service.IPasoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PasoServiceImpl implements IPasoService {

    @Autowired
    private IPasoRepository pasoRepository;

    @Autowired
    private PasoFactoryManager factoryManager;

    @Override
    public List<Paso> crearPasos(String proceso) {
        return this.factoryManager.generarPasosPorProceso(proceso);
    }

    @Override
    public Paso findById(Integer id) {
        return this.pasoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proceso con id: " + id + " no encontrado."));
    }

    @Override
    public Paso insert(Paso paso) {
        return this.pasoRepository.save(paso);
    }

}
