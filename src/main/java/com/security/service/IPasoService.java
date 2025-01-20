package com.security.service;

import java.util.List;
import java.util.Optional;

import com.security.db.Paso;

public interface IPasoService {


    public Paso findById(Integer id);

    public Optional<Paso> findByIdOptional(Integer id);

    public Paso insert(Paso paso);

    public Paso updateEstado(Integer idPaso, String estado);

    public List<String> buscarEstados();

}
