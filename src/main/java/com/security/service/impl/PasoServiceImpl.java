package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.factory.ProcesoPasoFactoryManager;
import com.security.repo.IPasoRepository;
import com.security.service.IPasoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PasoServiceImpl implements IPasoService {

    @Autowired
    private IPasoRepository procesoPasoRepository;

    @Autowired
    private ProcesoPasoFactoryManager factoryManager;

    @Override
    public List<Paso> crearPasos(String proceso) {
        return this.factoryManager.generarPasosPorProceso(proceso);
    }

    @Override
    public Paso findById(Integer id) {
        return this.procesoPasoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proceso con id: " + id + " no encontrado."));
    }

    @Override
    public Paso insert(Paso procesoPaso) {
        return this.procesoPasoRepository.save(procesoPaso);
    }

}
