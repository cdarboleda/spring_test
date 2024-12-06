package com.security.factory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.security.exception.CustomException;
import com.security.factory.concret.ProcesoPagoDocentes;
import com.security.factory.concret.ProcesoTitulacion;

@Component
public class ProcesoFactory {
    
    public ProcesoPlantilla createProceso(String tipo){
        switch (tipo) {
            case "pago-docentes":
                return new ProcesoPagoDocentes();
            case "titulacion":
                return new ProcesoTitulacion();       
            default:
            throw new CustomException("El tipo de proceso no existe", HttpStatus.BAD_REQUEST);
        }

    }
}
