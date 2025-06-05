package com.security.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.enums.Estado;
import com.security.repo.IPasoRepository;
import com.security.service.IPasoService;
import com.security.service.dto.PasoDTO;
import com.security.service.dto.utils.ConvertidorPaso;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PasoServiceImpl implements IPasoService {

    @Autowired
    private IPasoRepository pasoRepository;

    @Autowired
    private ConvertidorPaso convertidorPaso;

    @Override
    public Paso findById(Integer id) {
        return this.pasoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paso con id: " + id + " no encontrado."));
    }

    @Override
    public PasoDTO findByIdDTO(Integer id) {
        return convertidorPaso.convertirAPasoDTO(this.findById(id));
    }

    @Override
    public Optional<Paso> findByIdOptional(Integer id) {
        return this.pasoRepository.findById(id);
    }

    @Override
    public List<String> buscarEstados() {
        return Stream.of(Estado.values()).map((estado) -> estado.toString()).toList();
    }

}
