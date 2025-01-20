package com.security.factory.concret;

import org.springframework.stereotype.Component;

import com.security.factory.ProcesoPlantilla;

@Component
public class ProcesoTitulacionConcrete extends ProcesoPlantilla{

    public ProcesoTitulacionConcrete(){
        this.nombre = "Pago de Titulación";
        this.descripcion = "Aqui se titula";
    }
}
