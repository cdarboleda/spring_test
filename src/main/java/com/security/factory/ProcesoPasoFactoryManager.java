package com.security.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.security.db.Proceso;
import com.security.db.ProcesoPaso;
import com.security.factory.concret.PagoDocentePasoFactory;

@Component
public class ProcesoPasoFactoryManager {

    private static final Map<String, IProcesoPasoFactory> factoryMap = new HashMap<>();

    static {
        factoryMap.put("pago-docente", new PagoDocentePasoFactory());
    }

    public List<ProcesoPaso> generarPasosPorProceso(Proceso proceso) {
        IProcesoPasoFactory factory = factoryMap.get(proceso.getNombre());
        
        if (factory != null) {
            return factory.generatePasos(proceso);
        } else {
            System.out.println("No se han definido pasos para el proceso: " + proceso.getNombre());
            return new ArrayList<>();
        }
    }
    
}
