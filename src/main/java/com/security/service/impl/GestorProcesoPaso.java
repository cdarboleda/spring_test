package com.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Estado;
import com.security.db.ProcesoPaso;
import com.security.service.IEstadoService;
import com.security.service.IGestorProcesoPaso;
import com.security.service.IProcesoPasoService;

@Service
public class GestorProcesoPaso implements IGestorProcesoPaso {

    @Autowired
    private IProcesoPasoService pasoService;

    @Autowired
    private IEstadoService estadoService;

    @Override
    public ProcesoPaso updateEstado(Integer idPaso, Integer idEstado) {

        ProcesoPaso paso = this.pasoService.findById(idPaso);
        Estado estado = this.estadoService.findById(idEstado);

        paso.setEstado(estado);
        return this.pasoService.insert(paso);

    }

}
