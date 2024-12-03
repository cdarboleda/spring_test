package com.security.service;

import com.security.db.Proceso;



public interface IProcesoService {
    public Proceso findById(Integer id);
    public Proceso insert(Proceso proceso);
}
