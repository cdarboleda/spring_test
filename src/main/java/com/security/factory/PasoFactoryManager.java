package com.security.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.security.db.Paso;
import com.security.factory.concret.PagoDocentePasoFactory;

@Component
public class PasoFactoryManager {

    private final Map<String, IPasoFactory> factoryMap = new HashMap<>();

    @Autowired
    public PasoFactoryManager(PagoDocentePasoFactory pagoDocentePasoFactory) {
        factoryMap.put("pago-docentes", pagoDocentePasoFactory);
    }

    public List<Paso> generarPasosPorProceso(String proceso) {
        IPasoFactory factory = factoryMap.get(proceso);

        if (factory != null) {
            return factory.generatePasos();
        } else {
            System.out.println("No se han definido pasos para el proceso: " + proceso);
            return new ArrayList<>();
        }
    }

}
