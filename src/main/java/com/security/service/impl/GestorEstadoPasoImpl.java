package com.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Estado;
import com.security.db.Paso;
import com.security.service.IEstadoService;
import com.security.service.IGestorEstadoPaso;
import com.security.service.IPasoService;

@Service
public class GestorEstadoPasoImpl implements IGestorEstadoPaso {

    @Autowired
    private IPasoService pasoService;

    @Autowired
    private IEstadoService estadoService;

    @Override
    public Paso updateEstado(Integer idPaso, Integer idEstado) {

        Paso paso = this.pasoService.findById(idPaso);
        Estado estado = this.estadoService.findById(idEstado);

        paso.setEstado(estado);
        return this.pasoService.insert(paso);

    }

}
