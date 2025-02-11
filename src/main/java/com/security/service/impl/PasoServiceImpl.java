package com.security.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Paso;
import com.security.db.enums.Estado;
import com.security.db.enums.EstadoHelper;
import com.security.repo.IPasoRepository;
import com.security.service.IPasoService;
import com.security.service.dto.PasoDTO;

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
    public Paso updatePaso(Integer idPaso, PasoDTO pasoDTO) {
        Paso paso = this.findById(idPaso);

        paso.setEstado(Estado.valueOf(pasoDTO.getEstado()));
        
        if(pasoDTO.getObservacion()!=null)  paso.setObservacion(pasoDTO.getObservacion());
        paso.setDescripcionEstado(pasoDTO.getDescripcionEstado()==null?
        EstadoHelper.getDescripcionPorIndice(Estado.valueOf(pasoDTO.getEstado()), 0):pasoDTO.getDescripcionEstado());

        if (pasoDTO.getEstado().equalsIgnoreCase("FINALIZADO")) {
            paso.setFechaFin(LocalDateTime.now());
        } else if (pasoDTO.getEstado().equalsIgnoreCase("EN_CURSO")) {
            paso.setFechaInicio(LocalDateTime.now());
            paso.setFechaFin(null);
        } else if (pasoDTO.getEstado().equalsIgnoreCase("PENDIENTE")) {
            paso.setFechaInicio(null);
            paso.setFechaFin(null);
        }

        return paso; // Guarda los cambios
    }

    @Override
    public List<String> buscarEstados() {
        return Stream.of(Estado.values()).map((estado) -> estado.toString()).toList();
    }

}
