package com.security.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.security.db.Paso;
import com.security.db.enums.TipoProceso;
import com.security.factory.concret.PagoDocentePasoFactory;
import com.security.factory.concret.TitulacionPasoFactory;
import com.security.service.dto.PasoDTO;

@Component
public class PasoFactoryManager {

    private final Map<String, IPasoFactory> factoryMap = new HashMap<>();

    @Autowired
    public PasoFactoryManager(IPasoFactory procesoEspecifico) {
        factoryMap.put(TipoProceso.PAGO_DOCENTE.toString(), new PagoDocentePasoFactory());
        factoryMap.put(TipoProceso.TITULACION.toString(), new TitulacionPasoFactory());
    }

    public List<PasoDTO> generarPasosPorProceso(String proceso) {
        IPasoFactory factory = factoryMap.get(proceso);

        if (factory != null) {
            return factory.generatePasos();
        } else {
            System.out.println("No se han definido pasos para el proceso: " + proceso);
            return new ArrayList<>();
        }
    }

}