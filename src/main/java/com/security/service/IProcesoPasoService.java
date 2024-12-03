package com.security.service;

import java.util.List;

import com.security.db.ProcesoPaso;

public interface IProcesoPasoService {

    public Boolean insertMultipleProcesoPaso(List<ProcesoPaso> procesoPasos);
    
}
