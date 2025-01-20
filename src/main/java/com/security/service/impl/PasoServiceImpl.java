package com.security.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.enums.Estado;
import com.security.exception.CustomException;
import com.security.factory.PasoFactoryManager;
import com.security.repo.IPasoRepository;
import com.security.service.IPasoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PasoServiceImpl implements IPasoService {

    @Autowired
    private IPasoRepository pasoRepository;




    @Override
    public Paso findById(Integer id) {
        return this.pasoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paso con id: " + id + " no encontrado."));
    }

    @Override
    public Optional<Paso> findByIdOptional(Integer id) {
        return this.pasoRepository.findById(id);
    }

    @Override
    public Paso updateEstado(Integer idPaso, String estado) {
        if(!Estado.isValid(estado)){
            throw new CustomException("Estado no válido: " + estado, HttpStatus.BAD_REQUEST);
        }
        Paso paso = this.findById(idPaso);

        paso.setEstado(Estado.valueOf(estado.toUpperCase()));
        return paso; 
    }

    @Override
    public List<String> buscarEstados() {
        return Stream.of(Estado.values()).map((estado) -> estado.name().toLowerCase()).toList();
    }

    @Override
    public Paso insert(Paso paso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }



}
