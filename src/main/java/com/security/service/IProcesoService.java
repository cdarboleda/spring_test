package com.security.service;

import com.security.db.Proceso;

public interface IProcesoService {
    public Proceso findById(Integer id);
    public void delete(Integer id);
}
