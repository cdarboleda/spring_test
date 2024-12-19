package com.security.factory;

import java.util.List;

import com.security.db.Estado;
import com.security.db.Paso;

public interface IPasoFactory {

    public List<Paso> generatePasos();

    public Estado obtenerEstado(String nombreEstado);
    
}
