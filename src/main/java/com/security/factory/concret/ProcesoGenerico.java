package com.security.factory.concret;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.security.db.enums.Estado;
import com.security.db.enums.EstadoHelper;
import com.security.factory.IPasoFactory;
import com.security.service.dto.PasoDTO;

import jakarta.transaction.Transactional;

@Primary
@Component
public class ProcesoGenerico implements IPasoFactory {


    @Override
    @Transactional
    public List<PasoDTO> generatePasos() {
        List<PasoDTO> pasos = new ArrayList<>();
        pasos.add(this.crearPaso("Paso 1", 1, "Paso de prueba",
        Estado.EN_CURSO, EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO, 0), LocalDateTime.now()));

        return pasos;
    }

    private PasoDTO crearPaso(String nombre,
            Integer orden, 
            String descripcionPaso,
            Estado estado, 
            String descripcionEstado,
            LocalDateTime fechaInicio) {
                
        PasoDTO paso = new PasoDTO();
        paso.setNombre(nombre);
        paso.setOrden(orden);
        paso.setDescripcionPaso(nombre);
        paso.setDescripcionEstado(nombre);
        paso.setEstado(estado.toString());
        paso.setFechaInicio(fechaInicio);
        return paso;
    
    }
    
}
