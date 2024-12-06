package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Estado;
import com.security.exception.CustomException;
import com.security.repo.IEstadoRepository;
import com.security.service.IEstadoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoServiceImpl implements IEstadoService{
    @Autowired
    private IEstadoRepository estadoRepository;

    @Override
    public Estado findById(Integer id) {
        Estado estado = this.estadoRepository.findById(id)
        .orElseThrow(()-> new EntityNotFoundException("No hay estado con id: " + id));
        return estado;
    }

    @Override
    public List<Estado> findAll() {
        List<Estado> estados = this.estadoRepository.findAll();
        if (estados.isEmpty()) {
            throw new EntityNotFoundException("No hay estados");
        }
        return estados;
    }


    @Override
    public Estado insert(Estado estado) {
        if (estado == null) throw new CustomException("Error en los campos enviados", HttpStatus.BAD_REQUEST);
        estado.setId(null);
        return this.estadoRepository.save(estado);
    }

    @Override
    public Estado update(Estado estado) {
        if (estado == null) throw new CustomException("Error en los campos enviados", HttpStatus.BAD_REQUEST);
        Estado estadoActual = this.findById(estado.getId());
        estadoActual.setNombre(estado.getNombre());
        estadoActual.setDescripcion(estado.getDescripcion());
        estadoActual.setTipo(estado.getTipo());
        return estadoActual;
    }

    @Override
    public void delete(Integer id) {
        this.findById(id);
        this.estadoRepository.deleteById(id);
    }
    
}
