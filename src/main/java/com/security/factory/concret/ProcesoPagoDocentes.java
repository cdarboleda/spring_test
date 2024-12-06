package com.security.factory.concret;

import org.springframework.stereotype.Component;

import com.security.factory.ProcesoPlantilla;

@Component
public class ProcesoPagoDocentes extends ProcesoPlantilla{

    public ProcesoPagoDocentes(){
        this.nombre = "Pago de Docente";
        this.descripcion = "Aqui se paga al docente descripcion arreglar";
    }
}
