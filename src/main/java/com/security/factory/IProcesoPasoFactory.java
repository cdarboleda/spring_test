package com.security.factory;

import java.util.List;

import com.security.db.Proceso;
import com.security.db.ProcesoPaso;

public interface IProcesoPasoFactory {

    public List<ProcesoPaso> generatePasos(Proceso proceso);
    
}
