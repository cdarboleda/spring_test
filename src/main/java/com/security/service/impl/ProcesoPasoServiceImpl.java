package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.ProcesoPaso;
import com.security.factory.ProcesoPasoFactoryManager;
import com.security.repo.IProcesoPasoRepository;
import com.security.service.IProcesoPasoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProcesoPasoServiceImpl implements IProcesoPasoService {

    @Autowired
    private IProcesoPasoRepository procesoPasoRepository;

    @Autowired
    private ProcesoPasoFactoryManager factoryManager;

    @Override
    public List<ProcesoPaso> crearPasos(String proceso) {
        return this.factoryManager.generarPasosPorProceso(proceso);
    }

    @Override
    public ProcesoPaso findById(Integer id) {
        return this.procesoPasoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No existe el paso con id "+id));
    }

    @Override
    public ProcesoPaso insert(ProcesoPaso procesoPaso) {
        return this.procesoPasoRepository.save(procesoPaso);
    }


}
