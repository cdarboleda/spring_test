package com.security.factory.concret;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.security.db.enums.Estado;
import com.security.db.enums.EstadoHelper;
import com.security.factory.IPasoFactory;
import com.security.service.dto.PasoDTO;

import jakarta.transaction.Transactional;
//Crea pasos predefinidos para cada tipo de proceso,
//Tiene el primer paso como EN_CURSO, debe tener responsable y se asigna en el service que usa este metodo
//El resto de pasos estan en pendiente y sin responsable
@Component
public class TitulacionPasoFactory implements IPasoFactory {

    @Override
    @Transactional
    public List<PasoDTO> generatePasos() {
        List<PasoDTO> pasos = new ArrayList<>();
        pasos.add(this.crearPaso("Subir documentación", 1, "Aqui se sube la documentación",
         Estado.EN_CURSO, EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO, 0), LocalDateTime.now(),"docente"));
        pasos.add(this.crearPaso("Revisión documentación", 2,  "Coordinarción me revisa la documentación",
        Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,"coordinador"));
        pasos.add(this.crearPaso("Finalzacion", 3,  "Se ha realizao el pago", 
        Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,"secretaria"));
        return pasos;
    }

    private PasoDTO crearPaso(String nombre,
            Integer orden, 
            String descripcionPaso,
            Estado estado, 
            String descripcionEstado,
            LocalDateTime fechaInicio,
            String rol) {
                
        PasoDTO paso = new PasoDTO();
        paso.setNombre(nombre);
        paso.setOrden(orden);
        paso.setDescripcionPaso(descripcionPaso);
        paso.setDescripcionEstado(descripcionEstado);
        paso.setEstado(estado.toString());
        paso.setFechaInicio(fechaInicio);
        paso.setRol(rol);
        return paso;
    
    }

}
