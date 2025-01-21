package com.security.factory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.security.exception.CustomException;
import com.security.factory.concret.ProcesoPagoDocenteConcrete;
import com.security.factory.concret.ProcesoTitulacionConcrete;

@Component
public class ProcesoFactory {
    
    public ProcesoPlantilla createProceso(String tipo){
        switch (tipo) {
            case "PAGO_DOCENTE":
                return new ProcesoPagoDocenteConcrete();
            case "TITULACION":
                return new ProcesoTitulacionConcrete();       
            default:
            throw new CustomException("El tipo de proceso no existe", HttpStatus.BAD_REQUEST);
        }

    }
}
