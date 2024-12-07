package com.security.service;

import com.security.db.ProcesoPaso;

public interface IGestorProcesoPaso {
    
    public ProcesoPaso updateEstado(Integer idPaso, Integer idEstado);

}
