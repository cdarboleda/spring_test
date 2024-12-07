package com.security.factory;

import java.util.List;

import com.security.db.Estado;
import com.security.db.ProcesoPaso;

public interface IProcesoPasoFactory {

    public List<ProcesoPaso> generatePasos();

    public Estado obtenerEstado(String nombreEstado);
    
}
