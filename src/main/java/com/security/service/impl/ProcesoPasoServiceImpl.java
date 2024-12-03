package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.ProcesoPaso;
import com.security.repo.IProcesoPasoRepository;
import com.security.service.IProcesoPasoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProcesoPasoServiceImpl implements IProcesoPasoService{

    @Autowired
    private IProcesoPasoRepository procesoPasoRepository;

    @Override
    public Boolean insertMultipleProcesoPaso(List<ProcesoPaso> procesoPasos) {
        
        try {
            this.procesoPasoRepository.saveAll(procesoPasos);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
}
