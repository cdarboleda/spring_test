package com.security.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Proceso;
import com.security.db.enums.TipoProceso;
import com.security.exception.CustomException;
import com.security.repo.IProcesoRepository;
import com.security.service.IProcesoService;
import com.security.service.dto.ProcesoCompletoDTO;
import com.security.service.dto.ProcesoCompletoTitulacionDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProcesoServiceImpl implements IProcesoService {

    @Autowired
    private IProcesoRepository procesoRepository;

    @Override
    public Proceso findById(Integer id) {
        Proceso proceso = procesoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay proceso con id: " + id));

        return proceso;
    }

    @Override
    public void delete(Integer id) {
        if (!this.procesoRepository.existsById(id)) {
            throw new EntityNotFoundException("No hay proceso con id: " + id);
        }
        this.procesoRepository.deleteById(id);
    }

    public Proceso insert(Proceso proceso) {
        return this.procesoRepository.save(proceso);
    }

}
