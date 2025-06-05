package com.security.service;

import java.util.List;
import java.util.Optional;

import com.security.db.Paso;
import com.security.service.dto.PasoDTO;

public interface IPasoService {


    public Paso findById(Integer id);
    public PasoDTO findByIdDTO(Integer id);

    public Optional<Paso> findByIdOptional(Integer id);

    public List<String> buscarEstados();

}
