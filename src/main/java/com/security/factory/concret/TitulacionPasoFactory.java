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
        pasos.add(this.crearPaso("Registro Propuesta", 1, "Se registra la propuesta de proyecto",
                Estado.EN_CURSO, EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO, 0), LocalDateTime.now()));
        pasos.add(this.crearPaso("Desarrollo Proyecto", 2,
                "Se asigna el tutor y se reciben los documentos del proyecto finalizado",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null));
        pasos.add(this.crearPaso("Revisión Lectores", 3, "Se asignan los lectores y se emiten las observaciones ",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null));
        pasos.add(this.crearPaso("Defensa", 4,
                "Se reciben y revisan los documentos finales y se asigna fecha y trubunal de defensa",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null));
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
