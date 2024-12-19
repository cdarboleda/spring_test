package com.security.service;

import java.util.List;

import com.security.db.Paso;

public interface IPasoService {

    public List<Paso> crearPasos(String proceso);

    public Paso findById(Integer id);

    public Paso insert(Paso paso);

}
