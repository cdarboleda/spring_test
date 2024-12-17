package com.security.service;

import com.security.db.Paso;

public interface IGestorEstadoPaso {
    
    public Paso updateEstado(Integer idPaso, Integer idEstado);

}
