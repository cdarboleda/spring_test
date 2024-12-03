package com.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.security.db.Proceso;
import com.security.factory.ProcesoPasoFactoryManager;
import com.security.service.IProcesoLogService;

import jakarta.transaction.Transactional;

public class GestorProcesoPaso {

    @Autowired
    private IProcesoLogService logService;

    @Autowired
    private ProcesoPasoFactoryManager factoryManager;

    @Transactional
    public void insertar(Proceso proceso){

        //ProcesoLog log =  this.logService.insert(proceso);

        //factory.generarPasosPorProceso(log);

        //factoryManager.generarPasosPorProceso(log);


    }

    
}
