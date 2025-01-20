package com.security.factory.concret;

import org.springframework.stereotype.Component;

import com.security.factory.ProcesoPlantilla;

@Component
public class ProcesoPagoDocenteConcrete extends ProcesoPlantilla{

    public ProcesoPagoDocenteConcrete(){
        this.nombre = "Pago de Docente";
        this.descripcion = "Aqui se paga al docente descripcion arreglar";
    }
}
