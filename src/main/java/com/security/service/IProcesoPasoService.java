package com.security.service;

import java.util.List;

import com.security.db.ProcesoPaso;

public interface IProcesoPasoService {

    public List<ProcesoPaso> crearPasos(String proceso);

    public ProcesoPaso findById(Integer id);

    public ProcesoPaso insert(ProcesoPaso procesoPaso);


}
